package net.enorme.NER.entity.custom.indigo_salamander.goal;

import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class NapCycleGoal extends Goal {

    private static final int SLEEP_DURATION = 1200;   // 60 sec
    private static final int WANDER_DURATION = 600;   // 30 sec

    private enum Phase { SLEEPING, WANDERING, RETURNING }

    private final IndigoSalamanderEntity salamander;
    private Phase phase = Phase.SLEEPING;
    private int timer = 0;

    public NapCycleGoal(IndigoSalamanderEntity salamander) {
        this.salamander = salamander;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return salamander.level().dimension() == Level.NETHER
                && isOnValidSleepBlock();
    }

    @Override
    public boolean canContinueToUse() {
        if (isPlayerTooClose()) {
            return false;
        }
        return salamander.level().dimension() == Level.NETHER;
    }

    private boolean isPlayerTooClose() {
        Player nearby = salamander.level().getNearestPlayer(salamander, 2.0);
        if (nearby == null) return false;
        if (nearby.isSpectator() || nearby.isCreative()) return false;
        return !nearby.isShiftKeyDown();
    }

    @Override
    public void stop() {
        salamander.stopNapping();
        phase = Phase.WANDERING;
        timer = 0;
    }

    private boolean isOnValidSleepBlock() {
        BlockPos belowPos = salamander.blockPosition().below();
        BlockState belowState = salamander.level().getBlockState(belowPos);
        return belowState.is(Blocks.NETHERRACK);
    }

    @Override
    public void start() {
        phase = Phase.SLEEPING;
        timer = 0;
        if (salamander.getNestPos() == null) {
            salamander.setNestPos(salamander.blockPosition().below());
        }
        salamander.startNapping();
    }

    @Override
    public void tick() {
        timer++;

        switch (phase) {
            case SLEEPING -> {
                if (!isOnValidSleepBlock() || isPlayerTooClose()) {
                    salamander.stopNapping();
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                    return;
                }
                if (timer >= SLEEP_DURATION) {
                    salamander.stopNapping();
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                }
            }
            case WANDERING -> {
                if (isOnValidSleepBlock() && !isPlayerTooClose()) {
                    // trouvé du netherrack en errant — on adopte ce nouvel emplacement
                    salamander.setNestPos(salamander.blockPosition().below());
                    salamander.startNapping();
                    phase = Phase.SLEEPING;
                    timer = 0;
                    return;
                }
                if (timer >= WANDER_DURATION) {
                    phase = Phase.RETURNING;
                    timer = 0;
                    returnToNest();
                } else if (salamander.getNavigation().isDone()) {
                    wanderStep();
                }
            }
            case RETURNING -> {
                BlockPos nest = salamander.getNestPos();
                if (isOnValidSleepBlock() && !isPlayerTooClose()) {
                    // arrivée sur du netherrack valide, même si ce n'est plus exactement le nid d'origine
                    salamander.setNestPos(salamander.blockPosition().below());
                    salamander.startNapping();
                    phase = Phase.SLEEPING;
                    timer = 0;
                } else if (nest != null && salamander.blockPosition().closerThan(nest, 1.5)) {
                    // arrivée au nid, mais bloc cassé entre-temps -> repart errer plutôt que dormir dans le vide
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                } else if (salamander.getNavigation().isDone()) {
                    returnToNest();
                }
            }
        }
    }

    private void wanderStep() {
        var target = net.minecraft.world.entity.ai.util.DefaultRandomPos.getPos(salamander, 10, 3);
        if (target != null) {
            salamander.getNavigation().moveTo(target.x, target.y, target.z, 1.0);
        }
    }

    private void returnToNest() {
        BlockPos nest = salamander.getNestPos();
        if (nest != null) {
            salamander.getNavigation().moveTo(nest.getX() + 0.5, nest.getY(), nest.getZ() + 0.5, 1.0);
        }
    }
}
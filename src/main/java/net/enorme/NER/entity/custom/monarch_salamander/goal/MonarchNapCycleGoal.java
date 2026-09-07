package net.enorme.NER.entity.custom.monarch_salamander.goal;

import net.enorme.NER.entity.custom.monarch_salamander.MonarchSalamanderEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class MonarchNapCycleGoal extends Goal {

    private static final int SLEEP_DURATION = 1200;   // 60 sec
    private static final int WANDER_DURATION = 600;   // 30 sec

    private enum Phase { SLEEPING, WANDERING, RETURNING }

    private final MonarchSalamanderEntity monarchSalamander;
    private Phase phase = Phase.SLEEPING;
    private int timer = 0;

    public MonarchNapCycleGoal(MonarchSalamanderEntity monarchSalamander) {
        this.monarchSalamander = monarchSalamander;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return monarchSalamander.level().dimension() == Level.NETHER
                && isOnValidSleepBlock();
    }

    @Override
    public boolean canContinueToUse() {
        if (isPlayerTooClose()) {
            return false;
        }
        return monarchSalamander.level().dimension() == Level.NETHER;
    }

    private boolean isPlayerTooClose() {
        Player nearby = monarchSalamander.level().getNearestPlayer(monarchSalamander, 2.0);
        if (nearby == null) return false;
        if (nearby.isSpectator() || nearby.isCreative()) return false;
        return !nearby.isShiftKeyDown();
    }

    @Override
    public void stop() {
        monarchSalamander.stopNapping();
        phase = Phase.WANDERING;
        timer = 0;
    }

    private boolean isOnValidSleepBlock() {
        BlockPos belowPos = monarchSalamander.blockPosition().below();
        BlockState belowState = monarchSalamander.level().getBlockState(belowPos);
        return belowState.is(Blocks.NETHERRACK);
    }

    @Override
    public void start() {
        phase = Phase.SLEEPING;
        timer = 0;
        if (monarchSalamander.getNestPos() == null) {
            monarchSalamander.setNestPos(monarchSalamander.blockPosition().below());
        }
        monarchSalamander.startNapping();
    }

    @Override
    public void tick() {
        timer++;

        switch (phase) {
            case SLEEPING -> {
                if (!isOnValidSleepBlock() || isPlayerTooClose()) {
                    monarchSalamander.stopNapping();
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                    return;
                }
                if (timer >= SLEEP_DURATION) {
                    monarchSalamander.stopNapping();
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                }
            }
            case WANDERING -> {
                if (isOnValidSleepBlock() && !isPlayerTooClose()) {
                    // trouvé du netherrack en errant — on adopte ce nouvel emplacement
                    monarchSalamander.setNestPos(monarchSalamander.blockPosition().below());
                    monarchSalamander.startNapping();
                    phase = Phase.SLEEPING;
                    timer = 0;
                    return;
                }
                if (timer >= WANDER_DURATION) {
                    phase = Phase.RETURNING;
                    timer = 0;
                    returnToNest();
                } else if (monarchSalamander.getNavigation().isDone()) {
                    wanderStep();
                }
            }
            case RETURNING -> {
                BlockPos nest = monarchSalamander.getNestPos();
                if (isOnValidSleepBlock() && !isPlayerTooClose()) {
                    // arrivée sur du netherrack valide, même si ce n'est plus exactement le nid d'origine
                    monarchSalamander.setNestPos(monarchSalamander.blockPosition().below());
                    monarchSalamander.startNapping();
                    phase = Phase.SLEEPING;
                    timer = 0;
                } else if (nest != null && monarchSalamander.blockPosition().closerThan(nest, 1.5)) {
                    // arrivée au nid, mais bloc cassé entre-temps -> repart errer plutôt que dormir dans le vide
                    phase = Phase.WANDERING;
                    timer = 0;
                    wanderStep();
                } else if (monarchSalamander.getNavigation().isDone()) {
                    returnToNest();
                }
            }
        }
    }

    private void wanderStep() {
        var target = net.minecraft.world.entity.ai.util.DefaultRandomPos.getPos(monarchSalamander, 10, 3);
        if (target != null) {
            monarchSalamander.getNavigation().moveTo(target.x, target.y, target.z, 1.0);
        }
    }

    private void returnToNest() {
        BlockPos nest = monarchSalamander.getNestPos();
        if (nest != null) {
            monarchSalamander.getNavigation().moveTo(nest.getX() + 0.5, nest.getY(), nest.getZ() + 0.5, 1.0);
        }
    }
}
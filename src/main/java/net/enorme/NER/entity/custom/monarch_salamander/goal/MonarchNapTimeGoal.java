package net.enorme.NER.entity.custom.monarch_salamander.goal;

import net.enorme.NER.entity.custom.monarch_salamander.MonarchSalamanderEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class MonarchNapTimeGoal extends Goal {

    private final MonarchSalamanderEntity monarchSalamander;

    public MonarchNapTimeGoal(MonarchSalamanderEntity salamander) {
        this.monarchSalamander = salamander;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }



    @Override
    public boolean canUse() {
        if (!monarchSalamander.canNap()) {
            return false; // cooldown after being hurt, or has a target
        }
        if (isPlayerTooClose()) {
            return false;
        }
        return isOnValidSleepBlock() || isNightInOverworld();
    }

    @Override
    public boolean canContinueToUse() {
        // If something else woke it (like hurt()), end the goal
        if (!monarchSalamander.isNapping()) {
            return false;
        }
        return canUse();
    }
    private boolean isOnValidSleepBlock() {
        if (monarchSalamander.level().dimension() != Level.OVERWORLD) {
            return false;
        }
        BlockPos belowPos = monarchSalamander.blockPosition().below();
        BlockState belowState = monarchSalamander.level().getBlockState(belowPos);
        return belowState.is(Blocks.NETHERRACK);
    }

    private boolean isPlayerTooClose() {
        Player nearby = monarchSalamander.level().getNearestPlayer(monarchSalamander, 2.0);
        if (nearby == null) return false;
        if (nearby.isSpectator() || nearby.isCreative()) return false;
        return !nearby.isShiftKeyDown();
    }

    private boolean isNightInOverworld() {
        Level level = monarchSalamander.level();
        if (level.dimension() != Level.OVERWORLD) {
            return false;
        }
        return !level.isDay();
    }

    @Override
    public void start() {
        monarchSalamander.getNavigation().stop();
        monarchSalamander.setDeltaMovement(monarchSalamander.getDeltaMovement().multiply(0, 1, 0));
        monarchSalamander.startNapping();

        if (isOnValidSleepBlock()) {
            monarchSalamander.setNestPos(monarchSalamander.blockPosition().below());
        }
    }


    @Override
    public void stop() {
        monarchSalamander.stopNapping();
    }
}
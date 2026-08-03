package net.enorme.NER.entity.custom.indigo_salamander.goal;

import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class NapTimeGoal extends Goal {

    private final IndigoSalamanderEntity salamander;

    public NapTimeGoal(IndigoSalamanderEntity salamander) {
        this.salamander = salamander;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (isPlayerTooClose()) {
            return false;
        }
        boolean result = isOnValidSleepBlock() || isNightInOverworld();
        return result;
    }

    private boolean isOnValidSleepBlock() {
        if (salamander.level().dimension() != Level.OVERWORLD) {
            return false;
        }
        BlockPos belowPos = salamander.blockPosition().below();
        BlockState belowState = salamander.level().getBlockState(belowPos);
        return belowState.is(Blocks.NETHERRACK);
    }

    @Override
    public boolean canContinueToUse() {
        if (isPlayerTooClose()) {
            return false;
        }
        return canUse();
    }

    private boolean isPlayerTooClose() {
        Player nearby = salamander.level().getNearestPlayer(salamander, 2.0);
        if (nearby == null) return false;
        if (nearby.isSpectator() || nearby.isCreative()) return false;
        return !nearby.isShiftKeyDown();
    }

    private boolean isNightInOverworld() {
        Level level = salamander.level();
        if (level.dimension() != Level.OVERWORLD) {
            return false;
        }
        return !level.isDay();
    }

    @Override
    public void start() {
        salamander.getNavigation().stop();
        salamander.setDeltaMovement(salamander.getDeltaMovement().multiply(0, 1, 0));
        salamander.startNapping();

        if (isOnValidSleepBlock()) {
            salamander.setNestPos(salamander.blockPosition().below());
        }
    }

    @Override
    public void stop() {
        salamander.stopNapping();
    }
}
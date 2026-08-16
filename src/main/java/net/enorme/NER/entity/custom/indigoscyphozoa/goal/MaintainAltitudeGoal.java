package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class MaintainAltitudeGoal extends Goal {

    private final IndigoScyphozoaEntity mob;

    public MaintainAltitudeGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        BlockPos ground = findGroundBelow();
        if (ground != null && mob.getY() - ground.getY() > IndigoScyphozoaEntity.MAX_HOVER_HEIGHT) {
            mob.setDeltaMovement(
                    mob.getDeltaMovement().add(0, -0.08, 0));
        }
    }

    private BlockPos findGroundBelow() {
        BlockPos start = mob.blockPosition();
        int minimumY = Math.max(mob.level().getMinBuildHeight(), start.getY() - 32);
        for (BlockPos pos = start; pos.getY() >= minimumY; pos = pos.below()) {
            if (mob.level().getBlockState(pos).isFaceSturdy(mob.level(), pos, net.minecraft.core.Direction.UP)) {
                return pos;
            }
        }
        return null;
    }
}

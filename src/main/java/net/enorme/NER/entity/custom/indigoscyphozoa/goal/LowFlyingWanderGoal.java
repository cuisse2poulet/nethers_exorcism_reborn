package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LowFlyingWanderGoal extends Goal {

    private final IndigoScyphozoaEntity mob;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int recalculateTicks;

    public LowFlyingWanderGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() == null && mob.getRandom().nextInt(8) == 0;
    }

    @Override
    public void start() {
        chooseDestination();
        recalculateTicks = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return mob.getTarget() == null
                && recalculateTicks < 100
                && mob.distanceToSqr(targetX, targetY, targetZ) > 3.0D;
    }

    @Override
    public void tick() {
        if (++recalculateTicks % 10 == 0) {
            mob.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.4D);
        }
    }

    private void chooseDestination() {
        for (int attempt = 0; attempt < 8; attempt++) {
            double x = mob.getX() + (mob.getRandom().nextDouble() - 0.5D) * 24.0D;
            double z = mob.getZ() + (mob.getRandom().nextDouble() - 0.5D) * 24.0D;
            BlockPos ground = findGround(BlockPos.containing(x, mob.getY() + 4.0D, z));

            if (ground != null) {
                targetX = x;
                targetY = ground.getY() + IndigoScyphozoaEntity.MIN_HOVER_HEIGHT
                        + mob.getRandom().nextInt(IndigoScyphozoaEntity.MAX_HOVER_HEIGHT);
                targetZ = z;
                mob.getMoveControl().setWantedPosition(targetX, targetY, targetZ, 1.4D);
                return;
            }
        }

        targetX = mob.getX();
        targetY = mob.getY() + 1.0D;
        targetZ = mob.getZ();
    }

    private BlockPos findGround(BlockPos start) {
        int minimumY = Math.max(mob.level().getMinBuildHeight(), start.getY() - 24);
        for (BlockPos pos = start; pos.getY() >= minimumY; pos = pos.below()) {
            if (mob.level().getBlockState(pos).isFaceSturdy(mob.level(), pos, net.minecraft.core.Direction.UP)
                    && mob.level().getBlockState(pos.above()).isAir()) {
                return pos;
            }
        }
        return null;
    }
}

package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

public class LowFlyingWanderGoal extends Goal {

    private final IndigoScyphozoaEntity mob;

    public LowFlyingWanderGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return mob.getNavigation().isDone() && mob.getRandom().nextInt(20) == 0;
    }

    @Override
    public void start() {

        double x = mob.getX() + (mob.getRandom().nextDouble() - 0.5) * 16;
        double z = mob.getZ() + (mob.getRandom().nextDouble() - 0.5) * 16;

        BlockPos ground = mob.level().getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlockPos.containing(x, mob.getY(), z));

        double y = ground.getY() + 1 + mob.getRandom().nextInt(5);

        mob.getMoveControl().setWantedPosition(x, y, z, 1.0);
    }
}
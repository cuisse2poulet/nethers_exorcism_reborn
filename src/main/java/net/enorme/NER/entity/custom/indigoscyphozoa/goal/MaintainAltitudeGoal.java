package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

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

        BlockPos ground = mob.level().getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                mob.blockPosition());

        double distance = mob.getY() - ground.getY();

        if (distance > 5) {
            mob.setDeltaMovement(
                    mob.getDeltaMovement().add(0, -0.03, 0));
        }
    }
}
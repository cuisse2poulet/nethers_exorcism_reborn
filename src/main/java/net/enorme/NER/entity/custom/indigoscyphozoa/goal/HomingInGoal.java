package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class HomingInGoal extends Goal {
    private final IndigoScyphozoaEntity mob;
    private float angle;

    public HomingInGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null;
    }

    @Override
    public void tick() {

        LivingEntity target = mob.getTarget();

        angle += 0.1F;

        double radius = 4;

        double x = target.getX() + Math.cos(angle) * radius;
        double z = target.getZ() + Math.sin(angle) * radius;
        double y = target.getY() + 2;

        mob.getMoveControl().setWantedPosition(
                x, y, z, 1.2);
    }
}

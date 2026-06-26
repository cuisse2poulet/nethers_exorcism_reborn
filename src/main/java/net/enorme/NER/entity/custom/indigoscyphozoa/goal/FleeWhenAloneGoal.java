package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class FleeWhenAloneGoal extends Goal {

    private final IndigoScyphozoaEntity mob;

    public FleeWhenAloneGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {

        int count = mob.level()
                .getEntitiesOfClass(
                        IndigoScyphozoaEntity.class,
                        mob.getBoundingBox().inflate(12))
                .size();

        return count == 1 && mob.getLastHurtByMob() != null;
    }

    @Override
    public void tick() {

        LivingEntity attacker = mob.getLastHurtByMob();

        if (attacker == null)
            return;

        Vec3 away = mob.position()
                .subtract(attacker.position())
                .normalize()
                .scale(8);

        mob.getMoveControl().setWantedPosition(
                mob.getX() + away.x,
                mob.getY() + 2,
                mob.getZ() + away.z,
                1.5);
    }
}
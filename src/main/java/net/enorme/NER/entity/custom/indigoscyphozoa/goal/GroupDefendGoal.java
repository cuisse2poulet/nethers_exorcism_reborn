package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

import java.util.List;

public class GroupDefendGoal extends TargetGoal {

    private LivingEntity attacker;

    public GroupDefendGoal(IndigoScyphozoaEntity mob) {
        super(mob, false);
    }

    @Override
    public boolean canUse() {

        LivingEntity lastAttacker = mob.getLastHurtByMob();

        if (lastAttacker == null)
            return false;

        int groupSize = mob.level()
                .getEntitiesOfClass(
                        IndigoScyphozoaEntity.class,
                        mob.getBoundingBox().inflate(12))
                .size();

        if (groupSize < 2)
            return false;

        attacker = lastAttacker;

        return true;
    }

    @Override
    public void start() {

        LivingEntity attacker = mob.getLastHurtByMob();

        if (attacker == null)
            return;

        ScyphozoaGroupHelper.alertGroup((IndigoScyphozoaEntity) mob, attacker);
    }
}
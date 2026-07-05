package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.effect.ModEffect;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.joml.Vector3f;

public class HallucinationDefenseGoal extends Goal {

    private static final int GROUP_RADIUS = 12;
    private static final double CLOUD_RADIUS = 3.0;
    private static final int COOLDOWN = 100;

    private final IndigoScyphozoaEntity mob;
    private int cooldown;

    public HallucinationDefenseGoal(IndigoScyphozoaEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {

        if (cooldown > 0) {
            cooldown--;
        }

        if (mob.hurtTime != 10 || cooldown > 0)
            return;

        LivingEntity attacker = mob.getLastHurtByMob();
        if (attacker == null || attacker instanceof IndigoScyphozoaEntity)
            return;

        int groupSize = mob.level()
                .getEntitiesOfClass(
                        IndigoScyphozoaEntity.class,
                        mob.getBoundingBox().inflate(GROUP_RADIUS))
                .size();

        if (groupSize < 2)
            return;

        int amplifier = Math.min(groupSize - 2, 4);

        if (!(mob.level() instanceof ServerLevel level))
            return;

        level.sendParticles(
                new DustParticleOptions(
                        new Vector3f(0F, 1F, 0F),
                        1.5F),
                mob.getX(),
                mob.getY() + 0.5,
                mob.getZ(),
                75,
                CLOUD_RADIUS,
                1,
                CLOUD_RADIUS,
                0.02
        );

        attacker.addEffect(
                new MobEffectInstance(
                        ModEffect.HALLUCINATION,
                        200,
                        amplifier
                )
        );

        cooldown = COOLDOWN;
    }
}

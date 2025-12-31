package net.enorme.NER.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;


// adds a new Instant Cure Effect
public class InstantCureEffect extends MobEffect {
    public InstantCureEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    // Makes Instant cure do something on tick
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        for (MobEffectInstance effect : livingEntity.getActiveEffects()) {
            if (!effect.getEffect().value().isBeneficial()) {
                livingEntity.removeEffect(effect.getEffect());
                //System.out.println(effect.getEffect().value().getDisplayName());
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}


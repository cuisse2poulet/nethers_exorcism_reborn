package net.enorme.NER.item;

import net.enorme.NER.effect.ModEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties RAW_INDIGO_SALAMANDER_TAIL = (new FoodProperties.Builder().nutrition(4)
            .saturationModifier(0.45f).effect(() -> new MobEffectInstance(MobEffects.POISON, 160, 1), 1.0f )).build();

    public static final FoodProperties COOKED_INDIGO_SALAMANDER_TAIL =(new FoodProperties.Builder().nutrition(6).saturationModifier(0.55f)
            .effect(() -> new MobEffectInstance(ModEffect.INSTANT_CURE_EFFECT, 180, 0), 1.0f)).build();
}

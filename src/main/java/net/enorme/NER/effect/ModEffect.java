package net.enorme.NER.effect;

import net.enorme.NER.NethersExorcismMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffect {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NethersExorcismMod.MODID);

    public static final Holder<MobEffect> INSTANT_CURE_EFFECT = MOB_EFFECTS.register("instant_cure",
            () -> new InstantCureEffect(MobEffectCategory.BENEFICIAL, 0xBA6BDD));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}

package net.enorme.NER.potion;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.effect.ModEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, NethersExorcismMod.MODID);

    public static final Holder<Potion> INSTANT_CURE_POTION = POTIONS.register("instant_cure_potion",
            () -> new Potion(new MobEffectInstance(ModEffect.INSTANT_CURE_EFFECT, 1200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}

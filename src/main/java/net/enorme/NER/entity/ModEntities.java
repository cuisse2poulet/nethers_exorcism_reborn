package net.enorme.NER.entity;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NethersExorcismMod.MODID);

    public static final Supplier<EntityType<IndigoScyphozoaEntity>> SCYPHOZOA =
            ENTITY_TYPES.register("gecko", () -> EntityType.Builder.of(IndigoScyphozoaEntity::new, MobCategory.CREATURE)
                    .sized(0.75f, 0.35f).build("gecko"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
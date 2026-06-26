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
            ENTITY_TYPES.register("indigo_scyphozoa",
                    () -> EntityType.Builder.of(
                                    IndigoScyphozoaEntity::new,
                                    MobCategory.CREATURE)
                            .sized(1.2F, 3.5F)
                            .build("indigo_scyphozoa"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
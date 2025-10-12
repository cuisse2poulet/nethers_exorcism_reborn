package net.enorme.NER.worldgen;


import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.worldgen.tree.IndigoTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, NethersExorcismMod.MODID);

    public static final DeferredHolder<Feature<?>, IndigoTreeFeature> INDIGO_TREE =
            FEATURES.register("indigo_tree", () -> new IndigoTreeFeature(TreeConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }


}
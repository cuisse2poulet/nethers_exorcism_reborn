package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> DNA_FOREST_VEGETATION =
            registerKey("dna_forest_vegetation");
    public static final ResourceKey<PlacedFeature> DNA_FOREST_VEGETATION_BONEMEAL =
            registerKey("dna_forest_vegetation_bonemeal");

    public static final ResourceKey<PlacedFeature> INDIGO_ROOT_PATCH =
            registerKey("indigo_root_patch");
    public static final ResourceKey<PlacedFeature> INDIGO_SPROUTS_PATCH =
            registerKey("indigo_sprouts_patch");
    public static final ResourceKey<PlacedFeature> INDIGO_FUNGUS_PATCH =
            registerKey("indigo_fungus_patch");
    public static final ResourceKey<PlacedFeature> INDIGO_CANDLESPIRE_PATCH =
            registerKey("indigo_candlespire_patch");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, INDIGO_ROOT_PATCH,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.INDIGO_ROOT_BONEMEAL),
                List.of()
        );
        register(context, INDIGO_SPROUTS_PATCH,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL),
                List.of()
        );
        register(context, INDIGO_FUNGUS_PATCH,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.INDIGO_FUNGUS_BONEMEAL),
                List.of()
        );
        register(context, INDIGO_CANDLESPIRE_PATCH,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.INDIGO_CANDLESPIRE_BONEMEAL),
                List.of()
        );

        register(context, DNA_FOREST_VEGETATION,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DNA_FOREST_VEGETATION),
                List.of()
        );
        register(context, DNA_FOREST_VEGETATION_BONEMEAL,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DNA_FOREST_VEGETATION_BONEMEAL),
                List.of()
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
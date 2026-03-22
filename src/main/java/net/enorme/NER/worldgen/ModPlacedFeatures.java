package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> INDIGO_VEGETATION =
            registerKey("indigo_vegetation");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, INDIGO_VEGETATION,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DNA_FOREST_VEGETATION_BONEMEAL),
                List.of(
                        CountOnEveryLayerPlacement.of(3),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(
                                        BlockPos.ZERO.below(),
                                        ModBlocks.INDIGO_NYLIUM.get()
                                )
                        ),
                        BiomeFilter.biome()
                )
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
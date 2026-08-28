package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> INDIGO_VEGETATION =
            registerKey("indigo_vegetation");

    public static final ResourceKey<PlacedFeature> INDIGO_TREES =
            registerKey("indigo_trees");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Ground vegetation - covers the forest floor
        register(context, INDIGO_VEGETATION,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DNA_FOREST_VEGETATION_WORLDGEN),
                List.of(
                        CountOnEveryLayerPlacement.of(5),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(
                                        BlockPos.ZERO.below(),
                                        ModBlocks.INDIGO_NYLIUM.get()
                                )
                        ),
                        BiomeFilter.biome()
                )
        );

        // FIX (round 2): HeightRangePlacement picked a uniformly random Y across
        // a wide range, independent of where the actual nylium floor sits at
        // that column - so it almost never landed exactly on the floor, and
        // your custom IndigoTreeFeature has no internal ground-search fallback
        // (unlike vanilla's HugeFungusFeature, which validates valid_base_block
        // itself). Swapped to CountOnEveryLayerPlacement, the same approach your
        // working indigo_vegetation feature already uses: it scans the whole
        // column for surface transitions instead of guessing a Y, so it finds
        // the real floor regardless of terrain shape.
        //
        // Added InSquarePlacement.spread() so the (very large, 32-64 tall)
        // canopy isn't always rooted at the same corner of the chunk.
        // Even distribution fix: CountPlacement.of(3) rolled 3 independent
        // random x/z attempts PER CHUNK, so some chunks got several trees
        // landing near each other (clump) while neighboring chunks got none
        // that survived the footing/lava check (gap). Capping to exactly 1
        // attempt per chunk removes the possibility of intra-chunk clumping
        // entirely. RarityFilter then controls how many chunks get that one
        // attempt, giving roughly grid-spaced (not perfectly regular, but far
        // more even) distribution instead of pure random scatter.
        //
        // Tuning: onAverageOnceEvery(1) = try every chunk (densest, still
        // even). Raise the number for a sparser forest while keeping spacing
        // even - e.g. onAverageOnceEvery(2) averages one attempt every other
        // chunk, roughly ~32 blocks apart given the canopy's ~10-12 block
        // diameter, comfortably non-overlapping.
        register(context, INDIGO_TREES,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.INDIGO_TREES),
                List.of(
                        RarityFilter.onAverageOnceEvery(1),
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        CountOnEveryLayerPlacement.of(1),
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
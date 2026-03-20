package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> INDIGO_KEY = registerKey("indigo");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DNA_FOREST_VEGETATION =
            registerKey("dna_forest_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DNA_FOREST_VEGETATION_BONEMEAL =
            registerKey("dna_forest_vegetation_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_ROOT_BONEMEAL =
            registerKey("indigo_root_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_SPROUTS_BONEMEAL =
            registerKey("indigo_sprouts_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_FUNGUS_BONEMEAL =
            registerKey("indigo_fungus_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_CANDLESPIRE_BONEMEAL =
            registerKey("indigo_candlespire_bonemeal");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        context.register(INDIGO_KEY, new ConfiguredFeature<>(
                ModFeatures.INDIGO_TREE.get(),
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.INDIGO_STEM.get()),
                        new StraightTrunkPlacer(1, 0, 0),
                        BlockStateProvider.simple(ModBlocks.INDIGO_WART_BLOCK.get()),
                        new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 4),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).build()
        ));

        FeatureUtils.register(context, INDIGO_ROOT_BONEMEAL,
                ModFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        BlockStateProvider.simple(ModBlocks.INDIGO_ROOTS.get()),
                        7,
                        1
                )
        );

        FeatureUtils.register(context, INDIGO_SPROUTS_BONEMEAL,
                ModFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        BlockStateProvider.simple(ModBlocks.INDIGO_SPROUTS.get()),
                        7,
                        1
                )
        );

        FeatureUtils.register(context, INDIGO_FUNGUS_BONEMEAL,
                ModFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        BlockStateProvider.simple(ModBlocks.INDIGO_FUNGUS.get()),
                        7,
                        1
                )
        );

        FeatureUtils.register(context, INDIGO_CANDLESPIRE_BONEMEAL,
                ModFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        BlockStateProvider.simple(ModBlocks.INDIGO_CANDLESPIRE.get()),
                        7,
                        1
                )
        );

        FeatureUtils.register(context, DNA_FOREST_VEGETATION,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_ROOT_PATCH), 0.545f),
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_SPROUTS_PATCH), 0.364f),
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_FUNGUS_PATCH), 0.091f)
                        ),
                        placed.getOrThrow(ModPlacedFeatures.INDIGO_CANDLESPIRE_PATCH)
                )
        );

        FeatureUtils.register(context, DNA_FOREST_VEGETATION_BONEMEAL,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_ROOT_PATCH), 0.545f),
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_SPROUTS_PATCH), 0.364f),
                                new WeightedPlacedFeature(placed.getOrThrow(ModPlacedFeatures.INDIGO_FUNGUS_PATCH), 0.091f)
                        ),
                        placed.getOrThrow(ModPlacedFeatures.INDIGO_CANDLESPIRE_PATCH)
                )
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }
}
package net.enorme.NER.worldgen;

import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.utils.CommonUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class NERConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_KEY = registerKey("indigo");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_TREES = registerKey("indigo_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DNA_FOREST_VEGETATION_BONEMEAL =
            registerKey("dna_forest_vegetation_bonemeal");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> DNA_FOREST_VEGETATION_WORLDGEN =
            registerKey("dna_forest_vegetation_worldgen");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // Single tree configuration
        context.register(INDIGO_KEY, new ConfiguredFeature<>(
                NERFeatures.INDIGO_TREE.get(),
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(NERBlocks.INDIGO_STEM.get()),
                        new StraightTrunkPlacer(1, 0, 0),
                        BlockStateProvider.simple(NERBlocks.INDIGO_WART_BLOCK.get()),
                        new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 4),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).build()
        ));
        
        // Trees for world generation (sparse placement)
        context.register(INDIGO_TREES, new ConfiguredFeature<>(
                NERFeatures.INDIGO_TREE.get(),
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(NERBlocks.INDIGO_STEM.get()),
                        new StraightTrunkPlacer(1, 0, 0),
                        BlockStateProvider.simple(NERBlocks.INDIGO_WART_BLOCK.get()),
                        new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 4),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).build()
        ));

        // Vegetation for bonemeal
        FeatureUtils.register(context, DNA_FOREST_VEGETATION_BONEMEAL,
                NERFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(NERBlocks.INDIGO_ROOTS.get().defaultBlockState(), 70)
                                .add(NERBlocks.INDIGO_SPROUTS.get().defaultBlockState(), 20)
                                .add(NERBlocks.INDIGO_FUNGUS.get().defaultBlockState(), 8)
                                .add(NERBlocks.INDIGO_CANDLESPIRE.get().defaultBlockState(), 3)
                                .add(NERBlocks.INDIGO_COILSPROUT.get().defaultBlockState(), 3)
                                .build()),
                        3,
                        4
                )
        );
        
        // Vegetation for world generation (more dense)
        FeatureUtils.register(context, DNA_FOREST_VEGETATION_WORLDGEN,
                NERFeatures.INDIGO_FOREST_VEGETATION.get(),
                new NetherForestVegetationConfig(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(NERBlocks.INDIGO_ROOTS.get().defaultBlockState(), 85)
                                .add(NERBlocks.INDIGO_SPROUTS.get().defaultBlockState(), 30)
                                .add(NERBlocks.INDIGO_FUNGUS.get().defaultBlockState(), 10)
                                .add(NERBlocks.INDIGO_CANDLESPIRE.get().defaultBlockState(), 8)
                                .add(NERBlocks.INDIGO_COILSPROUT.get().defaultBlockState(), 8)
                                .build()),
                        8,
                        4
                )
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                CommonUtils.resourcePath(name));
    }
}
package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> INDIGO_KEY = registerKey("indigo");

    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_FOREST_VEGETATION_BONEMEAL =
            registerKey("indigo_forest_vegetation_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_SPROUTS_BONEMEAL =
            registerKey("indigo_sprouts_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INDIGO_COILSPROUT_BONEMEAL =
            registerKey("indigo_coilsprout_bonemeal");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // Tree for sapling growth
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

        FeatureUtils.register(context, INDIGO_FOREST_VEGETATION_BONEMEAL,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INDIGO_FUNGUS.get()))
                )
        );

        FeatureUtils.register(context, INDIGO_SPROUTS_BONEMEAL,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INDIGO_SPROUTS.get()))
                )
        );

        FeatureUtils.register(context, INDIGO_COILSPROUT_BONEMEAL,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INDIGO_COILSPROUT.get()))
                )
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }
}
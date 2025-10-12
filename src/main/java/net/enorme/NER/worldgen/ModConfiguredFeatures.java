package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;


public class ModConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?,?>> INDIGO_KEY = registerKey("indigo");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<? , ?>> context){
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
    }

    public static ResourceKey<ConfiguredFeature<? , ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }
}

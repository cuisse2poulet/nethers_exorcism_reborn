package net.enorme.NER.worldgen;

import net.enorme.NER.NethersExorcismMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_DNA_FOREST_TREES = registerKey("add_dna_forest_trees");
    public static final ResourceKey<BiomeModifier> ADD_DNA_FOREST_VEGETATION = registerKey("add_dna_forest_vegetation");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
/*
        context.register(ADD_DNA_FOREST_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(NetherBiomeRegion.DNA_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.INDIGO_TREES)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_DNA_FOREST_VEGETATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(NetherBiomeRegion.DNA_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.INDIGO_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));*/
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }
}

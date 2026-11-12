package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.worldgen.NERBiomeModifiers;
import net.enorme.NER.worldgen.NERConfiguredFeatures;
import net.enorme.NER.worldgen.NERPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NERDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, NERConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, NERPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NERBiomeModifiers::bootstrap)
            .add(Registries.JUKEBOX_SONG, NERJukeboxSongs::bootstrap);


    public NERDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NethersExorcismMod.MODID));
    }
}

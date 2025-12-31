package net.enorme.NER.datagen;

import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        add(ModBlocks.INDIGO_SlAB.get(),
                block -> createSlabItemTable(ModBlocks.INDIGO_SlAB.get()));

        dropSelf(ModBlocks.INDIGO_PLANKS.get());
        dropSelf(ModBlocks.INDIGO_STEM.get());
        dropSelf(ModBlocks.STRIPPED_INDIGO_STEM.get());
        dropSelf(ModBlocks.INDIGO_HYPHAE.get());
        dropSelf(ModBlocks.STRIPPED_INDIGO_HYPHAE.get());
        dropSelf(ModBlocks.INDIGO_STAIRS.get());
        dropSelf(ModBlocks.INDIGO_FENCE.get());
        dropSelf(ModBlocks.INDIGO_FENCE_GATE.get());
        dropSelf(ModBlocks.INDIGO_TRAPDOOR.get());
        dropSelf(ModBlocks.INDIGO_BUTTON.get());
        dropSelf(ModBlocks.INDIGO_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.INDIGO_WART_BLOCK.get());
        dropSelf(ModBlocks.VERDANT_SHROOMLIGHT.get());
        dropSelf(ModBlocks.INDIGO_FUNGUS.get());
        dropWhenSilkTouch(ModBlocks.INDIGO_NYLIUM.get());
        add(ModBlocks.INDIGO_DOOR.get(),
                block -> createDoorTable(ModBlocks.INDIGO_DOOR.get()));


        //for ores:
        // add(ModBlocks.YOURBLOCK.get(), block -> createOreDrop(ModBlocks.YOURBLOCK.get(),ModItems.YOURITEM.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

package net.enorme.NER.datagen;

import net.enorme.NER.block.NERBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class NERBlockLootTableProvider extends BlockLootSubProvider {

    protected NERBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        add(NERBlocks.INDIGO_SlAB.get(),
                block -> createSlabItemTable(NERBlocks.INDIGO_SlAB.get()));

        dropSelf(NERBlocks.INDIGO_PLANKS.get());
        dropSelf(NERBlocks.INDIGO_STEM.get());
        dropSelf(NERBlocks.STRIPPED_INDIGO_STEM.get());
        dropSelf(NERBlocks.INDIGO_HYPHAE.get());
        dropSelf(NERBlocks.STRIPPED_INDIGO_HYPHAE.get());
        dropSelf(NERBlocks.INDIGO_STAIRS.get());
        dropSelf(NERBlocks.INDIGO_FENCE.get());
        dropSelf(NERBlocks.INDIGO_FENCE_GATE.get());
        dropSelf(NERBlocks.INDIGO_TRAPDOOR.get());
        dropSelf(NERBlocks.INDIGO_BUTTON.get());
        dropSelf(NERBlocks.INDIGO_PRESSURE_PLATE.get());
        dropSelf(NERBlocks.INDIGO_WART_BLOCK.get());
        dropSelf(NERBlocks.VERDANT_SHROOMLIGHT.get());
        dropSelf(NERBlocks.INDIGO_FUNGUS.get());
        dropSelf(NERBlocks.GLOWING_JELLY_BLOCK.get());
        dropWhenSilkTouch(NERBlocks.INDIGO_NYLIUM.get());
        dropWhenSilkTouch(NERBlocks.SALAMANDER_EGG.get());
        add(NERBlocks.INDIGO_DOOR.get(),
                block -> createDoorTable(NERBlocks.INDIGO_DOOR.get()));

        dropSelf(NERBlocks.DNA_VINE_POINT.get());
        dropOther(NERBlocks.DNA_VINE.get(), NERBlocks.DNA_VINE_POINT.get());

        dropSelf(NERBlocks.INDIGO_COILSPROUT_TOP.get());
        dropOther(NERBlocks.INDIGO_COILSPROUT.get(),NERBlocks.INDIGO_COILSPROUT_TOP.get());

        dropSelf(NERBlocks.INDIGO_CANDLESPIRE.get());

        dropSelf(NERBlocks.INDIGO_ROOTS.get());
        dropSelf(NERBlocks.INDIGO_SPROUTS.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return NERBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.NERBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NERItemTagProvider extends ItemTagsProvider {
    public NERItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NethersExorcismMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);

        tag(ItemTags.PLANKS)
                .add(NERBlocks.INDIGO_PLANKS.asItem());

        tag(NERTags.Items.INDIGO_STEMS)
                .add(NERBlocks.INDIGO_STEM.asItem())
                .add(NERBlocks.STRIPPED_INDIGO_STEM.asItem())
                .add(NERBlocks.STRIPPED_INDIGO_HYPHAE.asItem())
                .add(NERBlocks.INDIGO_HYPHAE.asItem());

        tag(ItemTags.NON_FLAMMABLE_WOOD)
                .add(NERBlocks.INDIGO_PLANKS.asItem())
                .add(NERBlocks.INDIGO_STEM.asItem())
                .add(NERBlocks.STRIPPED_INDIGO_STEM.asItem())
                .add(NERBlocks.STRIPPED_INDIGO_HYPHAE.asItem())
                .add(NERBlocks.INDIGO_HYPHAE.asItem())
                .add(NERBlocks.INDIGO_STAIRS.asItem())
                .add(NERBlocks.INDIGO_SlAB.asItem())
                .add(NERBlocks.INDIGO_DOOR.asItem())
                .add(NERBlocks.INDIGO_TRAPDOOR.asItem())
                .add(NERBlocks.INDIGO_FENCE.asItem())
                .add(NERBlocks.INDIGO_FENCE_GATE.asItem())
                .add(NERBlocks.INDIGO_PRESSURE_PLATE.asItem())
                .add(NERBlocks.INDIGO_BUTTON.asItem());
    }

    @Override
    public String getName() {
        return "NER Item Tags";
    }
}

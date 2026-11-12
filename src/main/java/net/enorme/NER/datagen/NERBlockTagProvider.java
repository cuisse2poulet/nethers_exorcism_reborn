package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.NERBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NERBlockTagProvider extends BlockTagsProvider {
    public NERBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NethersExorcismMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(NERBlocks.INDIGO_PLANKS.get())
                .add(NERBlocks.INDIGO_STEM.get())
                .add(NERBlocks.INDIGO_HYPHAE.get())
                .add(NERBlocks.STRIPPED_INDIGO_HYPHAE.get())
                .add(NERBlocks.STRIPPED_INDIGO_STEM.get())
                .add(NERBlocks.INDIGO_STAIRS.get())
                .add(NERBlocks.INDIGO_SlAB.get())
                .add(NERBlocks.INDIGO_FENCE.get())
                .add(NERBlocks.INDIGO_FENCE_GATE.get())
                .add(NERBlocks.INDIGO_DOOR.get())
                .add(NERBlocks.INDIGO_TRAPDOOR.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(NERBlocks.INDIGO_WART_BLOCK.get())
                .add(NERBlocks.VERDANT_SHROOMLIGHT.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(NERBlocks.INDIGO_NYLIUM.get());
        tag(BlockTags.ENDERMAN_HOLDABLE)
                .add(NERBlocks.INDIGO_NYLIUM.get());
        tag(BlockTags.MUSHROOM_GROW_BLOCK)
                .add(NERBlocks.INDIGO_NYLIUM.get());
        tag(BlockTags.NYLIUM)
                .add(NERBlocks.INDIGO_NYLIUM.get());
        tag(BlockTags.LOGS)
                .add(NERBlocks.INDIGO_STEM.get())
                .add(NERBlocks.INDIGO_HYPHAE.get());
        tag(BlockTags.FENCE_GATES)
                .add(NERBlocks.INDIGO_FENCE_GATE.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(NERBlocks.INDIGO_FENCE.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(NERBlocks.INDIGO_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(NERBlocks.INDIGO_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS)
                .add(NERBlocks.INDIGO_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(NERBlocks.INDIGO_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_SLABS)
                .add(NERBlocks.INDIGO_SlAB.get());
        tag(BlockTags.WOODEN_STAIRS)
                .add(NERBlocks.INDIGO_STAIRS.get());

        tag(NERTags.Blocks.NEVER_OBSTRUCT_NEST)
                .add(Blocks.VINE)
                .addTag(BlockTags.CAVE_VINES)
                .addTag(BlockTags.TALL_FLOWERS);

    }
    @Override
    public String getName() {
        return "NER Block Tags";
    }
}

package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NethersExorcismMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
tag(BlockTags.MINEABLE_WITH_AXE)
        .add(ModBlocks.INDIGO_PLANKS.get())
        .add(ModBlocks.INDIGO_STEM.get())
        .add(ModBlocks.INDIGO_HYPHAE.get())
        .add(ModBlocks.STRIPPED_INDIGO_HYPHAE.get())
        .add(ModBlocks.STRIPPED_INDIGO_STEM.get())
        .add(ModBlocks.INDIGO_STAIRS.get())
        .add(ModBlocks.INDIGO_SlAB.get())
        .add(ModBlocks.INDIGO_FENCE.get())
        .add(ModBlocks.INDIGO_FENCE_GATE.get())
        .add(ModBlocks.INDIGO_DOOR.get())
        .add(ModBlocks.INDIGO_TRAPDOOR.get());

tag(BlockTags.MINEABLE_WITH_HOE)
        .add(ModBlocks.INDIGO_WART_BLOCK.get())
        .add(ModBlocks.VERDANT_SHROOMLIGHT.get());
tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .add(ModBlocks.INDIGO_NYLIUM.get());
tag(BlockTags.ENDERMAN_HOLDABLE)
        .add(ModBlocks.INDIGO_NYLIUM.get());
tag(BlockTags.MUSHROOM_GROW_BLOCK)
        .add(ModBlocks.INDIGO_NYLIUM.get());
tag(BlockTags.NYLIUM)
        .add(ModBlocks.INDIGO_NYLIUM.get());
tag(BlockTags.LOGS)
        .add(ModBlocks.INDIGO_STEM.get())
        .add(ModBlocks.INDIGO_HYPHAE.get());
tag(BlockTags.FENCE_GATES)
        .add(ModBlocks.INDIGO_FENCE_GATE.get());
tag(BlockTags.WOODEN_FENCES)
        .add(ModBlocks.INDIGO_FENCE.get());
tag(BlockTags.WOODEN_DOORS)
        .add(ModBlocks.INDIGO_DOOR.get());
tag(BlockTags.WOODEN_TRAPDOORS)
        .add(ModBlocks.INDIGO_TRAPDOOR.get());
tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.INDIGO_BUTTON.get());
tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.INDIGO_PRESSURE_PLATE.get());
tag(BlockTags.WOODEN_SLABS)
        .add(ModBlocks.INDIGO_SlAB.get());
tag(BlockTags.WOODEN_STAIRS)
        .add(ModBlocks.INDIGO_STAIRS.get());

    }
}

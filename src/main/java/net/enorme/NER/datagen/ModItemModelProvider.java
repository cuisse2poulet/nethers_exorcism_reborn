package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NethersExorcismMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.COOKED_INDIGO_SALAMANDER_TAIL.get());
        basicItem(ModItems.RAW_INDIGO_SALAMANDER_TAIL.get());
        basicItem(ModItems.GENETIC_MIRACLE_MUSIC_DISC.get());
        basicItem(ModBlocks.INDIGO_FUNGUS.asItem());

        buttonItem(ModBlocks.INDIGO_BUTTON, ModBlocks.INDIGO_PLANKS);
        fenceItem(ModBlocks.INDIGO_FENCE, ModBlocks.INDIGO_PLANKS);
        basicItem(ModBlocks.INDIGO_DOOR.asItem());
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}

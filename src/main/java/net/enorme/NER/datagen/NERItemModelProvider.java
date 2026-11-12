package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.item.NERItems;
import net.enorme.NER.utils.CommonUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class NERItemModelProvider extends ItemModelProvider {
    public NERItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NethersExorcismMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(NERItems.COOKED_INDIGO_SALAMANDER_TAIL.get());
        basicItem(NERItems.RAW_INDIGO_SALAMANDER_TAIL.get());
        basicItem(NERItems.GENETIC_MIRACLE_MUSIC_DISC.get());
        basicItem(NERBlocks.INDIGO_FUNGUS.asItem());
        basicItem(NERItems.GLOWING_JELLY.get());
        buttonItem(NERBlocks.INDIGO_BUTTON, NERBlocks.INDIGO_PLANKS);
        fenceItem(NERBlocks.INDIGO_FENCE, NERBlocks.INDIGO_PLANKS);
        basicItem(NERBlocks.INDIGO_DOOR.asItem());
        basicItem(NERItems.INDIGO_SALAMANDER_BUCKET.get());
        basicItem(NERBlocks.SALAMANDER_EGG.asItem());
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", CommonUtils.resourcePath(
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", CommonUtils.resourcePath(
                        "block/" + baseBlock.getId().getPath()));
    }
}

package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.NERBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class NERBlockStateProvider extends BlockStateProvider {
    public NERBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NethersExorcismMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(NERBlocks.INDIGO_PLANKS);
        blockWithItem(NERBlocks.INDIGO_WART_BLOCK);
        blockWithItem(NERBlocks.VERDANT_SHROOMLIGHT);

        stairsBlock(NERBlocks.INDIGO_STAIRS.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()));
        slabBlock(NERBlocks.INDIGO_SlAB.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()), blockTexture(NERBlocks.INDIGO_PLANKS.get()));
        buttonBlock(NERBlocks.INDIGO_BUTTON.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()));
        pressurePlateBlock(NERBlocks.INDIGO_PRESSURE_PLATE.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()));

        fenceBlock(NERBlocks.INDIGO_FENCE.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()));
        fenceGateBlock(NERBlocks.INDIGO_FENCE_GATE.get(), blockTexture(NERBlocks.INDIGO_PLANKS.get()));


        doorBlockWithRenderType(NERBlocks.INDIGO_DOOR.get(), modLoc("block/indigo_door_bottom"), modLoc("block/indigo_door_top"), "cutout");
        trapdoorBlockWithRenderType(NERBlocks.INDIGO_TRAPDOOR.get(), modLoc("block/indigo_trapdoor"), true, "cutout");

        FungusBlock(NERBlocks.INDIGO_FUNGUS);

        crossPlantWithItem(NERBlocks.DNA_VINE);
        crossPlantWithItem(NERBlocks.DNA_VINE_POINT);

        crossPlantWithItemInvert(NERBlocks.INDIGO_COILSPROUT);
        crossPlantWithItemInvert(NERBlocks.INDIGO_COILSPROUT_TOP);

        doubleCrossPlantWithItem(NERBlocks.INDIGO_CANDLESPIRE);
        crossPlantWithItem(NERBlocks.INDIGO_SPROUTS);


        blockItem(NERBlocks.INDIGO_STAIRS);
        blockItem(NERBlocks.INDIGO_SlAB);
        blockItem(NERBlocks.INDIGO_PRESSURE_PLATE);
        blockItem(NERBlocks.INDIGO_FENCE_GATE);
        blockItem(NERBlocks.INDIGO_TRAPDOOR, "_bottom");
    }

    private void doubleCrossPlantWithItem(DeferredBlock<?> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();

        ResourceLocation bottomTexture = modLoc("block/" + name + "_bottom");
        ResourceLocation topTexture = modLoc("block/" + name + "_top");

        ModelFile bottom = models().cross(name + "_bottom", bottomTexture).renderType("cutout");
        ModelFile top = models().cross(name + "_top", topTexture).renderType("cutout");

        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                .addModels(ConfiguredModel.builder().modelFile(bottom).build())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                .addModels(ConfiguredModel.builder().modelFile(top).build());

        itemModels()
                .withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", topTexture);
    }


    private void crossPlantWithItem(DeferredBlock<?> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();

        simpleBlock(block,
                models().cross(name, blockTexture(block)).renderType("cutout"));

        itemModels().getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("minecraft:item/generated"))
                .texture("layer0", blockTexture(block));
    }

    private void crossPlantWithItemInvert(DeferredBlock<?> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();

        simpleBlock(block,
                models().cross(name, blockTexture(block)).renderType("cutout"));

        itemModels().getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("minecraft:item/generated"))
                .texture("layer0", blockTexture(block)).transforms()
                .transform(ItemDisplayContext.GUI).rotation(180f, 0f, 0f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(180f, 0f, 0f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(180f, 0f, 0f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(180f, 0f, 0f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(180f, 0f, 0f).end()
                .end();
    }

    private void FungusBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(),
                        blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockWithItem(DeferredBlock<?> deferedBlock) {
        simpleBlockWithItem(deferedBlock.get(), cubeAll(deferedBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("nethers_exorcism_reborn:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("nethers_exorcism_reborn:block/" + deferredBlock.getId().getPath() + appendix));
    }
}

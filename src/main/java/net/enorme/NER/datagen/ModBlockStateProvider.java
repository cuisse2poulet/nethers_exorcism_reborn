package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
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

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NethersExorcismMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.INDIGO_PLANKS);
        blockWithItem(ModBlocks.INDIGO_WART_BLOCK);
        blockWithItem(ModBlocks.VERDANT_SHROOMLIGHT);

        stairsBlock(ModBlocks.INDIGO_STAIRS.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()));
        slabBlock(ModBlocks.INDIGO_SlAB.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()), blockTexture(ModBlocks.INDIGO_PLANKS.get()));
        buttonBlock(ModBlocks.INDIGO_BUTTON.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()));
        pressurePlateBlock(ModBlocks.INDIGO_PRESSURE_PLATE.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()));

        fenceBlock(ModBlocks.INDIGO_FENCE.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()));
        fenceGateBlock(ModBlocks.INDIGO_FENCE_GATE.get(), blockTexture(ModBlocks.INDIGO_PLANKS.get()));


        doorBlockWithRenderType(ModBlocks.INDIGO_DOOR.get(), modLoc("block/indigo_door_bottom"), modLoc("block/indigo_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.INDIGO_TRAPDOOR.get(), modLoc("block/indigo_trapdoor"), true, "cutout");

        FungusBlock(ModBlocks.INDIGO_FUNGUS);

        crossPlantWithItem(ModBlocks.DNA_VINE);
        crossPlantWithItem(ModBlocks.DNA_VINE_POINT);

        crossPlantWithItemInvert(ModBlocks.INDIGO_COILSPROUT);
        crossPlantWithItemInvert(ModBlocks.INDIGO_COILSPROUT_TOP);

        doubleCrossPlantWithItem(ModBlocks.INDIGO_CANDLESPIRE);
        crossPlantWithItem(ModBlocks.INDIGO_ROOTS);
        crossPlantWithItem(ModBlocks.INDIGO_SPROUTS);


        blockItem(ModBlocks.INDIGO_STAIRS);
        blockItem(ModBlocks.INDIGO_SlAB);
        blockItem(ModBlocks.INDIGO_PRESSURE_PLATE);
        blockItem(ModBlocks.INDIGO_FENCE_GATE);
        blockItem(ModBlocks.INDIGO_TRAPDOOR, "_bottom");

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

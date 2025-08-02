package net.enorme.NER.block;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(NethersExorcismMod.MODID);

    public static final DeferredBlock<Block> INDIGO_PLANKS = register("indigo_planks",
    () -> new Block(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> INDIGO_STEM = register("indigo_stem",
            () -> new StrippableRotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> STRIPPED_INDIGO_STEM = register("stripped_indigo_stem",
            () -> new RotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> INDIGO_HYPHAE = register("indigo_hyphae",
            () -> new StrippableRotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> STRIPPED_INDIGO_HYPHAE = register("stripped_indigo_hyphae",
            () -> new RotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));



    public static final DeferredBlock<StairBlock> INDIGO_STAIRS = register("indigo_stairs",
            () -> new StairBlock(ModBlocks.INDIGO_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<SlabBlock> INDIGO_SlAB = register("indigo_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));


    public static final DeferredBlock<PressurePlateBlock> INDIGO_PRESSURE_PLATE = register("indigo_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<ButtonBlock> INDIGO_BUTTON = register("indigo_button",
            () -> new ButtonBlock(BlockSetType.CRIMSON,20, BlockBehaviour.Properties.of().strength(2f).noCollission().sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<FenceBlock> INDIGO_FENCE = register("indigo_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<FenceGateBlock> INDIGO_FENCE_GATE = register("indigo_fence_gate",
            () -> new FenceGateBlock(WoodType.CRIMSON, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<DoorBlock> INDIGO_DOOR = register("indigo_door",
            () -> new DoorBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD).noOcclusion()));

    public static final DeferredBlock<TrapDoorBlock> INDIGO_TRAPDOOR = register("indigo_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD).noOcclusion()));

    public static final DeferredBlock<Block> INDIGO_NYLIUM = register("indigo_nylium",
            () -> new Block(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.NYLIUM)));

    public static final DeferredBlock<Block> INDIGO_WART_BLOCK = register("indigo_wart_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).strength(1f).sound(SoundType.WART_BLOCK)));


    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name,()-> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventbus){
        BLOCKS.register(eventbus);
    }
}

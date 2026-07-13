package net.enorme.NER.block;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.custom.IndigoNyliumBlock;
import net.enorme.NER.block.custom.JellyBlock;
import net.enorme.NER.block.custom.plants.IndigoRootsBlock;
import net.enorme.NER.block.custom.plants.IndigoSproutsBlock;
import net.enorme.NER.block.custom.plants.IndigoCandleSpire;
import net.enorme.NER.block.custom.plants.CoilSproutBlock;
import net.enorme.NER.block.custom.plants.CoilSproutTopBlock;
import net.enorme.NER.block.custom.plants.DNAVinesBlock;
import net.enorme.NER.block.custom.plants.DNAVinesPointBlock;
import net.enorme.NER.block.custom.RotatableBlocks;
import net.enorme.NER.block.custom.StrippableRotatableBlocks;
import net.enorme.NER.item.ModItems;
import net.enorme.NER.worldgen.ModConfiguredFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(NethersExorcismMod.MODID);

    //Indigo Wood Set
    public static final DeferredBlock<Block> INDIGO_PLANKS = register("indigo_planks",
            () -> new Block(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<StairBlock> INDIGO_STAIRS = register("indigo_stairs",
            () -> new StairBlock(ModBlocks.INDIGO_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> INDIGO_STEM = register("indigo_stem",
            () -> new StrippableRotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> STRIPPED_INDIGO_STEM = register("stripped_indigo_stem",
            () -> new RotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> INDIGO_HYPHAE = register("indigo_hyphae",
            () -> new StrippableRotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<Block> STRIPPED_INDIGO_HYPHAE = register("stripped_indigo_hyphae",
            () -> new RotatableBlocks(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.STEM)));

    public static final DeferredBlock<SlabBlock> INDIGO_SlAB = register("indigo_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));


    public static final DeferredBlock<PressurePlateBlock> INDIGO_PRESSURE_PLATE = register("indigo_pressure_plate",
            () -> new PressurePlateBlock(NERBlockSetType.INDIGO, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<ButtonBlock> INDIGO_BUTTON = register("indigo_button",
            () -> new ButtonBlock(NERBlockSetType.INDIGO, 20, BlockBehaviour.Properties.of().strength(2f).noCollission().sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<FenceBlock> INDIGO_FENCE = register("indigo_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<FenceGateBlock> INDIGO_FENCE_GATE = register("indigo_fence_gate",
            () -> new FenceGateBlock(NERWoodType.INDIGO, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<DoorBlock> INDIGO_DOOR = register("indigo_door",
            () -> new DoorBlock(NERBlockSetType.INDIGO, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD).noOcclusion()));

    public static final DeferredBlock<TrapDoorBlock> INDIGO_TRAPDOOR = register("indigo_trapdoor",
            () -> new TrapDoorBlock(NERBlockSetType.INDIGO, BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD).noOcclusion()));

    public static final DeferredBlock<IndigoNyliumBlock> INDIGO_NYLIUM = register("indigo_nylium",
            () -> new IndigoNyliumBlock(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.NYLIUM)));

    public static final DeferredBlock<Block> INDIGO_FUNGUS = register("indigo_fungus",
            () -> new FungusBlock(
                    ModConfiguredFeatures.INDIGO_KEY,
                    ModBlocks.INDIGO_NYLIUM.get(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS)
            )
    );

    public static final DeferredBlock<Block> INDIGO_WART_BLOCK = register("indigo_wart_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).strength(1f).sound(SoundType.WART_BLOCK)));

    public static final DeferredBlock<Block> VERDANT_SHROOMLIGHT = register("verdant_shroomlight",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SHROOMLIGHT).strength(1f).sound(SoundType.SHROOMLIGHT)));


    public static final DeferredBlock<DNAVinesBlock> DNA_VINE = register("dna_vine",
            () -> new DNAVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT)));

    public static final DeferredBlock<DNAVinesPointBlock> DNA_VINE_POINT = register("dna_vine_point",
            () -> new DNAVinesPointBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES)));

    public static final DeferredBlock<CoilSproutBlock> INDIGO_COILSPROUT = register("indigo_coilsprout_bottom",
            () -> new CoilSproutBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES)));

    public static final DeferredBlock<CoilSproutTopBlock> INDIGO_COILSPROUT_TOP = register("indigo_coilsprout_top",
            () -> new CoilSproutTopBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES_PLANT)));

    public static final DeferredBlock<IndigoCandleSpire> INDIGO_CANDLESPIRE = register("indigo_candlespire",
            () -> new IndigoCandleSpire(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noOcclusion().noCollission()));

    public static final DeferredBlock<IndigoRootsBlock> INDIGO_ROOTS = register("indigo_roots",
            () -> new IndigoRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).noCollission().noOcclusion()));

    public static final DeferredBlock<IndigoSproutsBlock> INDIGO_SPROUTS = register("indigo_sprout",
            () -> new IndigoSproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).noCollission().noOcclusion()));

    public static final DeferredBlock<Block> GLOWING_JELLY_BLOCK = register("glowing_jelly_block",
            () -> new JellyBlock(JellyBlock.defaultProperties()));

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventbus) {
        BLOCKS.register(eventbus);
    }
}

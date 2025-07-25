package net.enorme.NER.block;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

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

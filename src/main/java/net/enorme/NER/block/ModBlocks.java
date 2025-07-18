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

import javax.swing.*;
import java.util.function.Supplier;

import static net.minecraft.world.item.Items.registerBlock;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(NethersExorcismMod.MODID);

    public static final DeferredBlock<Block> INDIGO_PLANKS = register("indigo_planks",
    () -> new Block(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.NETHER_WOOD)));


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

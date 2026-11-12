package net.enorme.NER.item;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.utils.CommonUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NERCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NethersExorcismMod.MODID);

    public static final Supplier<CreativeModeTab> DNA_FOREST_TAB = CREATIVE_MOD_TAB.register("dna_forest_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(NERItems.RAW_INDIGO_SALAMANDER_TAIL.get()))
                    .withTabsBefore(CommonUtils.resourcePath( "bio_deltas_tab"))
                    .title(Component.translatable("creativetab.nethers_exorcism_reborn.dna_forest_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(NERItems.RAW_INDIGO_SALAMANDER_TAIL);
                        output.accept(NERItems.COOKED_INDIGO_SALAMANDER_TAIL);
                        output.accept(NERItems.GENETIC_MIRACLE_MUSIC_DISC);
                        output.accept(NERBlocks.INDIGO_STEM);
                        output.accept(NERBlocks.STRIPPED_INDIGO_STEM);
                        output.accept(NERBlocks.INDIGO_HYPHAE);
                        output.accept(NERBlocks.STRIPPED_INDIGO_HYPHAE);
                        output.accept(NERBlocks.INDIGO_PLANKS);
                        output.accept(NERBlocks.INDIGO_STAIRS);
                        output.accept(NERBlocks.INDIGO_SlAB);
                        output.accept(NERBlocks.INDIGO_DOOR);
                        output.accept(NERBlocks.INDIGO_TRAPDOOR);
                        output.accept(NERBlocks.INDIGO_FENCE);
                        output.accept(NERBlocks.INDIGO_FENCE_GATE);
                        output.accept(NERBlocks.INDIGO_BUTTON);
                        output.accept(NERBlocks.INDIGO_PRESSURE_PLATE);
                        output.accept(NERBlocks.INDIGO_NYLIUM);
                        output.accept(NERBlocks.VERDANT_SHROOMLIGHT);
                        output.accept(NERBlocks.INDIGO_FUNGUS);
                        output.accept(NERBlocks.INDIGO_SPROUTS);
                        output.accept(NERBlocks.INDIGO_ROOTS);
                        output.accept(NERBlocks.INDIGO_WART_BLOCK);
                        output.accept(NERBlocks.INDIGO_CANDLESPIRE);
                        output.accept(NERBlocks.DNA_VINE_POINT);
                        output.accept(NERBlocks.INDIGO_COILSPROUT_TOP);
                        output.accept(NERItems.GLOWING_JELLY);
                        output.accept(NERItems.SCYPHOZOA_SPAWN_EGG);
                        output.accept(NERItems.SALAMANDER_SPAWN_EGG);
                        output.accept(NERBlocks.SALAMANDER_EGG);
                        output.accept(NERBlocks.GLOWING_JELLY_BLOCK);
                    }).build());

    public static final Supplier<CreativeModeTab> BIO_DELTAS_TAB = CREATIVE_MOD_TAB.register("bio_deltas_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(Blocks.BASALT.asItem()))
                    .title(Component.translatable("creativetab.nethers_exorcism_reborn.bio_deltas_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(NERItems.RAW_INDIGO_SALAMANDER_TAIL);
                    }).build());

    public static void register(IEventBus eventbus) {
        CREATIVE_MOD_TAB.register(eventbus);

    }
}

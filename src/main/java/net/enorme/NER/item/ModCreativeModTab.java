package net.enorme.NER.item;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTab {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
           DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NethersExorcismMod.MODID);

   public static void register(IEventBus eventbus) {
       CREATIVE_MOD_TAB.register(eventbus);

   }
    public static final Supplier<CreativeModeTab> DNA_FOREST_TAB = CREATIVE_MOD_TAB.register("dna_forest_tab",
            () -> CreativeModeTab.builder().icon(
                            ()-> new ItemStack(ModItems.RAW_INDIGO_SALAMANDER_TAIL.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, "bio_deltas_tab"))
                    .title(Component.translatable("creativetab.nethers_exorcism_reborn.dna_forest_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_INDIGO_SALAMANDER_TAIL);
                        output.accept(ModItems.COOKED_INDIGO_SALAMANDER_TAIL);
                        output.accept(ModBlocks.INDIGO_PLANKS);
                    }).build());

   public static final Supplier<CreativeModeTab> BIO_DELTAS_TAB = CREATIVE_MOD_TAB.register("bio_deltas_tab",
            () -> CreativeModeTab.builder().icon(
                            ()-> new ItemStack(Blocks.BASALT.asItem()))
                    .title(Component.translatable("creativetab.nethers_exorcism_reborn.bio_deltas_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_INDIGO_SALAMANDER_TAIL);
                    }).build());
}

package net.enorme.NER.item;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.entity.ModEntities;
import net.enorme.NER.sound.ModSound;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NethersExorcismMod.MODID);

    public static final DeferredItem<Item> RAW_INDIGO_SALAMANDER_TAIL = ITEMS.register("raw_indigo_salamander_tail",
            () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_INDIGO_SALAMANDER_TAIL)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.nethers_exorcism_reborn.raw_indigo_salamander_tail.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    public static final DeferredItem<Item> COOKED_INDIGO_SALAMANDER_TAIL = ITEMS.register("cooked_indigo_salamander_tail",
            () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_INDIGO_SALAMANDER_TAIL)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.nethers_exorcism_reborn.cooked_indigo_salamander_tail.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> GLOWING_JELLY = ITEMS.register("glowing_jelly",
            () -> new Item(new Item.Properties().food(ModFoodProperties.GLOWING_JELLY)){
        @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(Component.translatable("tooltip.nethers_exorcism_reborn.glowing_jelly.tooltip"));
                super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        }
    });


    public static final DeferredItem<Item> SCYPHOZOA_SPAWN_EGG = ITEMS.register("scyphozoa_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SCYPHOZOA, 0xFFFFFF, 0xFFFFFF,
                    new Item.Properties()));


    public static final DeferredItem<Item> GENETIC_MIRACLE_MUSIC_DISC = ITEMS.register("genetic_miracle_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSound.GENETIC_MIRACLE_KEY).stacksTo(1).rarity(Rarity.RARE)));

    public static final DeferredItem<BlockItem> WEIRD_GOOP_ITEM =
            ITEMS.register("weird_goop",
                    () -> new BlockItem(ModBlocks.GLOWING_JELLY_BLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

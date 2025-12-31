package net.enorme.NER.item;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.sound.ModSound;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
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


    public static final DeferredItem<Item> GENETIC_MIRACLE_MUSIC_DISC = ITEMS.register("genetic_miracle_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSound.GENETIC_MIRACLE_KEY).stacksTo(1).rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

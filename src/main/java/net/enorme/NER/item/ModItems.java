package net.enorme.NER.item;

import net.enorme.NER.NethersExorcismMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NethersExorcismMod.MODID);

    public static final DeferredItem<Item> RAW_INDIGO_SALAMANDER_TAIL = ITEMS.register("raw_indigo_salamander_tail",
            () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_INDIGO_SALAMANDER_TAIL)));


    public static final DeferredItem<Item> COOKED_INDIGO_SALAMANDER_TAIL = ITEMS.register("cooked_indigo_salamander_tail",
            () -> new Item(new Item.Properties().food(ModFoodProperties.COOKED_INDIGO_SALAMANDER_TAIL)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

package net.enorme.NER;

import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.effect.ModEffect;
import net.enorme.NER.item.ModCreativeModTab;
import net.enorme.NER.item.ModItems;
import net.enorme.NER.potion.ModPotions;
import net.enorme.NER.sound.ModSound;
import net.minecraft.world.level.ItemLike;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NethersExorcismMod.MODID)
public class NethersExorcismMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "nethers_exorcism_reborn";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public NethersExorcismMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffect.register(modEventBus);
        ModPotions.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        ModSound.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }


    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.RAW_INDIGO_SALAMANDER_TAIL);
            event.accept(ModItems.COOKED_INDIGO_SALAMANDER_TAIL);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.INDIGO_PLANKS);
            event.accept(ModBlocks.INDIGO_STEM);
            event.accept(ModBlocks.STRIPPED_INDIGO_STEM);
            event.accept(ModBlocks.INDIGO_HYPHAE);
            event.accept(ModBlocks.STRIPPED_INDIGO_HYPHAE);
            event.accept(ModBlocks.INDIGO_STAIRS);
            event.accept(ModBlocks.INDIGO_SlAB);
            event.accept(ModBlocks.INDIGO_DOOR);
            event.accept(ModBlocks.INDIGO_TRAPDOOR);
            event.accept(ModBlocks.INDIGO_FENCE);
            event.accept(ModBlocks.INDIGO_FENCE_GATE);
            event.accept(ModBlocks.INDIGO_BUTTON);
            event.accept(ModBlocks.INDIGO_PRESSURE_PLATE);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.GENETIC_MIRACLE_MUSIC_DISC);
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(ModBlocks.INDIGO_NYLIUM);
            event.accept(ModBlocks.VERDANT_SHROOMLIGHT);
            event.accept(ModBlocks.INDIGO_FUNGUS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}

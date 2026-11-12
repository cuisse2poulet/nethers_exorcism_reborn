package net.enorme.NER;

import com.mojang.logging.LogUtils;
import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.effect.NEREffect;
import net.enorme.NER.entity.NEREntities;
import net.enorme.NER.item.NERCreativeModTab;
import net.enorme.NER.item.NERItems;
import net.enorme.NER.potion.NERPotions;
import net.enorme.NER.sound.NERSound;
import net.enorme.NER.worldgen.NERFeatures;
import net.enorme.NER.worldgen.NERPlacementModifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import static net.enorme.NER.potion.NERPotions.INSTANT_CURE_POTION;
import static net.enorme.NER.potion.NERPotions.JELLYCIOUS_BREW_POTION;
import static net.minecraft.world.item.alchemy.Potions.POISON;

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
        NeoForge.EVENT_BUS.addListener(NethersExorcismMod::registerBrewingRecipes);
        NERCreativeModTab.register(modEventBus);

        NERItems.register(modEventBus);
        NERBlocks.register(modEventBus);
        NEREffect.register(modEventBus);
        NERPotions.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        NERSound.register(modEventBus);
        NEREntities.register(modEventBus);
        NERFeatures.register(modEventBus);
        NERPlacementModifiers.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NethersExorcismMod.MODID);


    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(NERItems.RAW_INDIGO_SALAMANDER_TAIL);
            event.accept(NERItems.COOKED_INDIGO_SALAMANDER_TAIL);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(NERBlocks.INDIGO_PLANKS);
            event.accept(NERBlocks.INDIGO_STEM);
            event.accept(NERBlocks.STRIPPED_INDIGO_STEM);
            event.accept(NERBlocks.INDIGO_HYPHAE);
            event.accept(NERBlocks.STRIPPED_INDIGO_HYPHAE);
            event.accept(NERBlocks.INDIGO_STAIRS);
            event.accept(NERBlocks.INDIGO_SlAB);
            event.accept(NERBlocks.INDIGO_DOOR);
            event.accept(NERBlocks.INDIGO_TRAPDOOR);
            event.accept(NERBlocks.INDIGO_FENCE);
            event.accept(NERBlocks.INDIGO_FENCE_GATE);
            event.accept(NERBlocks.INDIGO_BUTTON);
            event.accept(NERBlocks.INDIGO_PRESSURE_PLATE);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(NERItems.GENETIC_MIRACLE_MUSIC_DISC);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(NERBlocks.INDIGO_NYLIUM);
            event.accept(NERBlocks.VERDANT_SHROOMLIGHT);
            event.accept(NERBlocks.INDIGO_FUNGUS);
            event.accept(NERBlocks.INDIGO_ROOTS);
            event.accept(NERBlocks.INDIGO_SPROUTS);
            event.accept(NERBlocks.INDIGO_CANDLESPIRE);
            event.accept(NERBlocks.INDIGO_COILSPROUT_TOP);
            event.accept(NERBlocks.DNA_VINE_POINT);
        }
    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        // Gets the builder to add recipes to
        PotionBrewing.Builder builder = event.getBuilder();

        // Will add brewing recipes for all container potions (e.g. potion, splash potion, lingering potion)
        builder.addMix(
                // The initial potion to apply to
                Potions.MUNDANE,
                // The brewing ingredient. This is the item at the top of the brewing stand.
                NERItems.COOKED_INDIGO_SALAMANDER_TAIL.get(),
                        // The resulting potion
                        INSTANT_CURE_POTION
        );
                builder.addMix(
                        // The initial potion to apply to
                        Potions.AWKWARD,
                // The brewing ingredient. This is the item at the top of the brewing stand.
                NERItems.GLOWING_JELLY.get(),
                // The resulting potion
                JELLYCIOUS_BREW_POTION
        );
        builder.addMix(
                // The initial potion to apply to
                Potions.AWKWARD,
                // The brewing ingredient. This is the item at the top of the brewing stand.
                NERItems.RAW_INDIGO_SALAMANDER_TAIL.get(),
                // The resulting potion
                POISON
        );
    }


}

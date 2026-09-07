package net.enorme.NER;

import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.entity.ModEntities;
import net.enorme.NER.entity.client.indigo_salamander.IndigoSalamanderRenderer;
import net.enorme.NER.entity.client.indigoscyphozoa.IndigoScyphozoaRenderer;
import net.enorme.NER.entity.client.monarch_salamander.MonarchSalamanderRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NethersExorcismMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NethersExorcismMod.MODID, value = Dist.CLIENT)
public class NethersExorcismClient {
    public NethersExorcismClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_VINE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_VINE_POINT.get(), RenderType.cutout());
        });
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SALAMANDER.get(), IndigoSalamanderRenderer::new);
        event.registerEntityRenderer(ModEntities.SCYPHOZOA.get(), IndigoScyphozoaRenderer::new);
        event.registerEntityRenderer(ModEntities.MONARCHSALAMANDER.get(), MonarchSalamanderRenderer::new);

    }
}

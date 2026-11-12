package net.enorme.NER;

import net.enorme.NER.block.NERBlocks;
import net.enorme.NER.entity.NEREntities;
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
            ItemBlockRenderTypes.setRenderLayer(NERBlocks.DNA_VINE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(NERBlocks.DNA_VINE_POINT.get(), RenderType.cutout());
        });
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NEREntities.SALAMANDER.get(), IndigoSalamanderRenderer::new);
        event.registerEntityRenderer(NEREntities.SCYPHOZOA.get(), IndigoScyphozoaRenderer::new);
        event.registerEntityRenderer(NEREntities.MONARCHSALAMANDER.get(), MonarchSalamanderRenderer::new);

    }
}

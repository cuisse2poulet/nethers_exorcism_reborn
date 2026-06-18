package net.enorme.NER.event;

import net.enorme.NER.entity.ModEntities;
import net.enorme.NER.entity.client.indigoscyphozoa.IndigoScyphozoaRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ModClientEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(
                    ModEntities.SCYPHOZOA.get(),
                    IndigoScyphozoaRenderer::new
            );
        }
}

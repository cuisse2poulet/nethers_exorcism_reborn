package net.enorme.NER.event;


import net.enorme.NER.entity.ModEntities;
import net.enorme.NER.entity.client.indigoscyphozoa.IndigoScyphozoaModel;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber
public class ModEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IndigoScyphozoaModel.LAYER_LOCATION, IndigoScyphozoaModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SCYPHOZOA.get(), IndigoScyphozoaEntity.createAttributes().build());
    }
}
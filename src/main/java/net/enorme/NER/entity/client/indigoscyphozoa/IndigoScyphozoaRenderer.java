package net.enorme.NER.entity.client.indigoscyphozoa;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IndigoScyphozoaRenderer extends GeoEntityRenderer<IndigoScyphozoaEntity> {

    public IndigoScyphozoaRenderer(EntityRendererProvider.Context context) {
        super(context, new IndigoScyphozoaModel());

        this.shadowRadius = 0.25F;
    }
}
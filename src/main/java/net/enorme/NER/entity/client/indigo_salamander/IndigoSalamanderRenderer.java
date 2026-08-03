package net.enorme.NER.entity.client.indigo_salamander;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IndigoSalamanderRenderer extends GeoEntityRenderer<IndigoSalamanderEntity> {

    public IndigoSalamanderRenderer(EntityRendererProvider.Context context) {
        super(context, new IndigoSalamanderModel());

        this.shadowRadius = 0.15F;
        this.withScale(1.0f);
    }
    @Override
    public void render(IndigoSalamanderEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        this.withScale(entity.isBaby() ? 0.6f : 1.0f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
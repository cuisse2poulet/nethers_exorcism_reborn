package net.enorme.NER.entity.client.indigoscyphozoa;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class ScyphozoaMoodTransitionLayer extends GeoRenderLayer<IndigoScyphozoaEntity> {

    public ScyphozoaMoodTransitionLayer(GeoRenderer<IndigoScyphozoaEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, IndigoScyphozoaEntity entity,
                       BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {
        if (entity.getMoodTransitionTicks() <= 0) {
            return;
        }

        float progress = entity.getMoodTransitionProgress(partialTick);
        float easedProgress = progress * progress * (3.0F - 2.0F * progress);
        int alpha = Mth.ceil(easedProgress * 255.0F);
        if (alpha <= 0) {
            return;
        }

        ResourceLocation texture = ((IndigoScyphozoaModel) getGeoModel()).getMoodTexture(entity.getMood());
        RenderType transitionRenderType = RenderType.entityTranslucent(texture);
        VertexConsumer transitionBuffer = bufferSource.getBuffer(transitionRenderType);
        renderer.reRender(bakedModel, poseStack, bufferSource, entity,
                transitionRenderType, transitionBuffer, partialTick, packedLight,
                packedOverlay, Color.ofRGBA(255, 255, 255, alpha).getColor());
    }
}

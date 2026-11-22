package net.enorme.NER.entity.client.indigoscyphozoa;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IndigoScyphozoaRenderer extends GeoEntityRenderer<IndigoScyphozoaEntity> {

    private static final float MAX_LEAP_TILT_DEGREES = 75.0F;

    public IndigoScyphozoaRenderer(EntityRendererProvider.Context context) {
        super(context, new IndigoScyphozoaModel());
        addRenderLayer(new ScyphozoaMoodTransitionLayer(this));
        this.shadowRadius = 0.25F;
    }

    @Override
    public void render(IndigoScyphozoaEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float tilt = entity.getLeapTilt(partialTick);
        if (tilt <= 0.0F) {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            return;
        }

        float yawRad = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot()) * Mth.DEG_TO_RAD;
        float dirX = -Mth.sin(yawRad);
        float dirZ = Mth.cos(yawRad);
        float pivotY = entity.getBbHeight() * 0.5F;

        poseStack.pushPose();
        poseStack.translate(0.0F, pivotY, 0.0F);
        poseStack.mulPose(new Quaternionf().rotateAxis(
                tilt * MAX_LEAP_TILT_DEGREES * Mth.DEG_TO_RAD, dirZ, 0.0F, -dirX));
        poseStack.translate(0.0F, -pivotY, 0.0F);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}

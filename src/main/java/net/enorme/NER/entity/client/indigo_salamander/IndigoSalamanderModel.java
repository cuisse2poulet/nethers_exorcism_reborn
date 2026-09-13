package net.enorme.NER.entity.client.indigo_salamander;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class IndigoSalamanderModel extends GeoModel<IndigoSalamanderEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/indigo_salamander.geo.json");


    private static final ResourceLocation BABY_MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/baby_salamander.geo.json");

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/indigo_salamander.animation.json");

    private static final ResourceLocation BABY_ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/baby_salamander.animation.json");

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_salamander.png");

    private static final ResourceLocation BABY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/baby_salamander.png");

    @Override
    public void setCustomAnimations(IndigoSalamanderEntity animatable, long instanceId, AnimationState<IndigoSalamanderEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animatable.isNapping()) {
            return; // let the sleep animation control the head, don't override it
        }

        GeoBone head = getAnimationProcessor().getBone("head");
        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null && extraData != null) {
            float yaw = Mth.clamp(extraData.netHeadYaw(), -35f, 35f);
            float pitch = Mth.clamp(extraData.headPitch(), -30f, 30f);

            head.setRotX(pitch * Mth.DEG_TO_RAD);
            head.setRotY(yaw * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public ResourceLocation getModelResource(IndigoSalamanderEntity entity) {
        return entity.isBaby() ? BABY_MODEL : MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(IndigoSalamanderEntity entity) {
        return entity.isBaby() ? BABY_ANIMATION : ANIMATION;
    }

    @Override
    public ResourceLocation getTextureResource(IndigoSalamanderEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
        }

    }
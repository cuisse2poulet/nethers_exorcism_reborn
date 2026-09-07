package net.enorme.NER.entity.client.monarch_salamander;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.enorme.NER.entity.custom.monarch_salamander.MonarchSalamanderEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MonarchSalamanderModel extends GeoModel<MonarchSalamanderEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/monarch_salamander.geo.json");


    private static final ResourceLocation BABY_MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/monarch_salamander.geo.json");

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/monarch_salamander.animation.json");

    private static final ResourceLocation BABY_ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/monarch_salamander.animation.json");

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/monarch_salamander.png");

    private static final ResourceLocation BABY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/monarch_salamander.png");

    @Override
    public ResourceLocation getModelResource(MonarchSalamanderEntity entity) {
        return entity.isBaby() ? BABY_MODEL : MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(MonarchSalamanderEntity entity) {
        return entity.isBaby() ? BABY_ANIMATION : ANIMATION;
    }

    @Override
    public ResourceLocation getTextureResource(MonarchSalamanderEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    public void setCustomAnimations(MonarchSalamanderEntity animatable, long instanceId, AnimationState<MonarchSalamanderEntity> animationState) {
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

    }
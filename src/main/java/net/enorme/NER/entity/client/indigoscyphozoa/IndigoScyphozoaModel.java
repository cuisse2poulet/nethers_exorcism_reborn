package net.enorme.NER.entity.client.indigoscyphozoa;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.resources.ResourceLocation;

public class IndigoScyphozoaModel extends GeoModel<IndigoScyphozoaEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/indigo_scyphozoa.geo.json");

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/indigo_scyphozoa.animation.json");

    private static final ResourceLocation CALM =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_scyphozoa/scyphozoa_neutral.png");

    private static final ResourceLocation ANGRY =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_scyphozoa/scyphozoa_angry.png");

    private static final ResourceLocation HURT =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_scyphozoa/scyphozoa_hurt.png");

    private static final ResourceLocation LOVE =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_scyphozoa/scyphozoa_inlove.png");

    private static final ResourceLocation SAD =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigo_scyphozoa/scyphozoa_sad.png");

    @Override
    public ResourceLocation getModelResource(IndigoScyphozoaEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(IndigoScyphozoaEntity animatable) {
        return ANIMATION;
    }

    @Override
    public ResourceLocation getTextureResource(IndigoScyphozoaEntity entity) {

        return switch (entity.getMood()) {
            case LOVE -> LOVE;
            case HURT -> HURT;
            case ANGRY -> ANGRY;
            case SAD -> SAD;
            default -> CALM;
        };
    }
}
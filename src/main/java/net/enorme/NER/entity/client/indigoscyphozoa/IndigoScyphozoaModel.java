package net.enorme.NER.entity.client.indigoscyphozoa;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IndigoScyphozoaModel extends GeoModel<IndigoScyphozoaEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "geo/indigo_scyphozoa.geo.json");

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "animations/indigo_scyphozoa.animation.json");

    @Override
    public ResourceLocation getModelResource(IndigoScyphozoaEntity entity) {
        return MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(IndigoScyphozoaEntity entity) {
        return ANIMATION;
    }

    @Override
    public ResourceLocation getTextureResource(IndigoScyphozoaEntity entity) {
        return switch (entity.getMood()) {
            case LOVE -> ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigoscyphozoa/scyphozoa_inlove.png");

            case HURT -> ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigoscyphozoa/scyphozoa_hurt.png");

            case ANGRY -> ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigoscyphozoa/scyphozoa_angry.png");

            case SAD -> ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigoscyphozoa/scyphozoa_sad.png");

            default -> ResourceLocation.fromNamespaceAndPath(
                    NethersExorcismMod.MODID,
                    "textures/entity/indigoscyphozoa/scyphozoa_neutral.png");
        };
    }
}
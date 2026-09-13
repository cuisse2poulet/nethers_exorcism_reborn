package net.enorme.NER.utils;

import net.minecraft.resources.ResourceLocation;

public class CommonUtils {
    public static final String MOD_ID = "nethers_exorcism_reborn";

    public static ResourceLocation resourcePath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}

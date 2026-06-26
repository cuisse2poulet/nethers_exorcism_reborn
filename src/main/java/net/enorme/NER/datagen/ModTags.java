package net.enorme.NER.datagen;

import net.enorme.NER.NethersExorcismMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    private static TagKey<Block> createBlockTags(String name) {
        return TagKey.create(Registries.BLOCK,  ResourceLocation.fromNamespaceAndPath(NethersExorcismMod.MODID, name));
    }
}


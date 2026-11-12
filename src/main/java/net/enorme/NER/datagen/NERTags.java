package net.enorme.NER.datagen;

import net.enorme.NER.utils.CommonUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NERTags {

    public static class Blocks {
        public static final TagKey<Block> NEVER_OBSTRUCT_NEST = createTag("never_obstruct_nest");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, CommonUtils.resourcePath(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> INDIGO_STEMS = createTag("indigo_stems");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, CommonUtils.resourcePath(name));
        }
    }
}

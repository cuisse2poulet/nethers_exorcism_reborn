package net.enorme.NER.worldgen.tree;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower Indigo = new TreeGrower(NethersExorcismMod.MODID + ":indigo", Optional.empty()
            , Optional.of(ModConfiguredFeatures.INDIGO_KEY), Optional.empty());
}

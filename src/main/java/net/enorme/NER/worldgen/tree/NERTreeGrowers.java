package net.enorme.NER.worldgen.tree;

import net.enorme.NER.NethersExorcismMod;
import net.enorme.NER.worldgen.NERConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class NERTreeGrowers {
    public static final TreeGrower INDIGO = new TreeGrower(NethersExorcismMod.MODID + ":indigo", Optional.empty()
            , Optional.of(NERConfiguredFeatures.INDIGO_KEY), Optional.empty());
}

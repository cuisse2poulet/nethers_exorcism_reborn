package net.enorme.NER.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModNyliumBlocks extends NyliumBlock {
    public ModNyliumBlocks(Properties properties) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).randomTicks());
    }
}

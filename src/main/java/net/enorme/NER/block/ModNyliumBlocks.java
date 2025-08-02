package net.enorme.NER.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ModNyliumBlocks extends NyliumBlock{
    public ModNyliumBlocks(Properties properties) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).randomTicks());
    }
}

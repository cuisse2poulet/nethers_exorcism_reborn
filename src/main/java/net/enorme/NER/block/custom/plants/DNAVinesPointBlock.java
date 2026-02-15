package net.enorme.NER.block.custom.plants;

import com.mojang.serialization.MapCodec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DNAVinesPointBlock extends GrowingPlantHeadBlock {

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public static final MapCodec<DNAVinesPointBlock> CODEC = simpleCodec(DNAVinesPointBlock::new);

    public DNAVinesPointBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true, 0.1D);
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.DNA_VINE.get();
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ModBlocks.DNA_VINE_POINT.get();
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1 + randomSource.nextInt(2);
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return blockState.isAir();
    }
}
package net.enorme.NER.block.custom.plants;


import com.mojang.serialization.MapCodec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoilSproutTopBlock extends GrowingPlantHeadBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public static final MapCodec<CoilSproutTopBlock> CODEC = simpleCodec(CoilSproutTopBlock::new);

    public CoilSproutTopBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true, 0.1D);
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.INDIGO_COILSPROUT.get();
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1 + random.nextInt(3);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

}
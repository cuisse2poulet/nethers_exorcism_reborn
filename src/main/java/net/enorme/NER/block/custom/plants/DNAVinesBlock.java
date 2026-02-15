package net.enorme.NER.block.custom.plants;

import com.mojang.serialization.MapCodec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DNAVinesBlock extends GrowingPlantBodyBlock {

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public static final MapCodec<DNAVinesBlock> CODEC = simpleCodec(DNAVinesBlock::new);

    public DNAVinesBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true);
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.DNA_VINE.get();
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ModBlocks.DNA_VINE_POINT.get();
    }
}

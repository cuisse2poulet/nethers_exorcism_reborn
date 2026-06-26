package net.enorme.NER.block.custom.plants;

import com.mojang.serialization.MapCodec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IndigoSproutsBlock extends BushBlock {

    public static final MapCodec<IndigoSproutsBlock> CODEC = simpleCodec(IndigoSproutsBlock::new);

    public IndigoSproutsBlock(Properties properties) {
        super(properties);
    }

    protected static final VoxelShape SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 3.0F, 14.0F);


    @Override
    public MapCodec<IndigoSproutsBlock> codec() {
        return CODEC;
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.NYLIUM)  || state.is(Blocks.SOUL_SOIL) || state.is(ModBlocks.INDIGO_NYLIUM.get()) || super.mayPlaceOn(state, level, pos);
    }
}

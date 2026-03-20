package net.enorme.NER.worldgen.vegetation;

import com.mojang.serialization.Codec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;

public class IndigoForestVegetationFeature extends Feature<NetherForestVegetationConfig> {
    public IndigoForestVegetationFeature(Codec<NetherForestVegetationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NetherForestVegetationConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockState state = level.getBlockState(pos.below());
        NetherForestVegetationConfig config = context.config();
        RandomSource random = context.random();
        
        if (!state.is(ModBlocks.INDIGO_NYLIUM.get())) {
            return false;
        }

        int placed = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        
        for (int i = 0; i < config.spreadWidth * config.spreadWidth; i++) {
            mutablePos.setWithOffset(
                pos,
                random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth),
                random.nextInt(config.spreadHeight) - random.nextInt(config.spreadHeight),
                random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth)
            );

            if (mutablePos.getY() != pos.getY()) {
                continue;
            }

            if (level.isEmptyBlock(mutablePos) && config.stateProvider.getState(random, mutablePos).canSurvive(level, mutablePos)) {
                level.setBlock(mutablePos, config.stateProvider.getState(random, mutablePos), 2);
                placed++;
            }
        }

        return placed > 0;
    }
}

package net.enorme.NER.block.custom;

import net.enorme.NER.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class IndigoNyliumBlock extends Block implements BonemealableBlock {
    public IndigoNyliumBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    private static boolean canBeNylium(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = reader.getBlockState(above);
        int lightBlocked = LightEngine.getLightBlockInto(reader, state, pos, aboveState, above, Direction.UP, aboveState.getLightBlock(reader, above));
        return lightBlocked < reader.getMaxLightLevel();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeNylium(state, level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos placePos = pos.above();
        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

        for (int i = 0; i < 8; ++i) {
            BlockPos targetPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(3) - 1,
                    random.nextInt(3) - 1
            );

            BlockState targetState = level.getBlockState(targetPos);
            BlockState aboveTarget = level.getBlockState(targetPos.above());
            if (targetState.is(Blocks.NETHERRACK) && aboveTarget.isAir() && canBeNylium(state, level, targetPos)) {
                level.setBlockAndUpdate(targetPos, this.defaultBlockState());
            }
        }

        boolean placedAny = false;

        if (random.nextInt(16) == 0) { // 1/16 chance, was 1/8
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_FOREST_VEGETATION_BONEMEAL, level, chunkGenerator, random, placePos);
        }
        if (random.nextInt(6) == 0) { // 1/6 chance, was 1/3
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL, level, chunkGenerator, random, placePos);
        }
        if (random.nextInt(16) == 0) { // 1/16 chance, was 1/8
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_COILSPROUT_BONEMEAL, level, chunkGenerator, random, placePos);
        }
        if (!placedAny && random.nextBoolean()) {
            place(registry, ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL, level, chunkGenerator, random, placePos);
        }
    }

    private boolean place(
            Registry<ConfiguredFeature<?, ?>> featureRegistry,
            ResourceKey<ConfiguredFeature<?, ?>> featureKey,
            ServerLevel level,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BlockPos pos
    ) {
        return featureRegistry.getHolder(featureKey)
                .map(holder -> holder.value().place(level, chunkGenerator, random, pos))
                .orElse(false);
    }
}
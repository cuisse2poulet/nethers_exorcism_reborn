package net.enorme.NER.block.custom;

import net.enorme.NER.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
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
        int lightBlocked = LightEngine.getLightBlockInto(reader, state, pos, aboveState, above,
                Direction.UP, aboveState.getLightBlock(reader, above));
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
        if (state.getBlock() == this) {
            return level.getBlockState(pos.above()).isAir();
        }

        if (state.is(Blocks.NETHERRACK)) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos adjPos = pos.relative(dir);
                if (level.getBlockState(adjPos).getBlock() == this) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(Blocks.NETHERRACK)) {
            boolean foundNylium = false;
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos adjPos = pos.relative(dir);
                if (level.getBlockState(adjPos).getBlock() == this) {
                    foundNylium = true;
                    break;
                }
            }

            if (!foundNylium) {
                return;
            }

            level.setBlockAndUpdate(pos, this.defaultBlockState());

            growVegetation(level, random, pos, this.defaultBlockState());

            return;
        }

        if (state.getBlock() == this) {
            growVegetation(level, random, pos, state);
        }
    }

    /**
     * Grows Indigo vegetation above this nylium block. No nylium spreading here.
     */
    private void growVegetation(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos placePos = pos.above();
        if (!level.getBlockState(placePos).isAir()) {
            return;
        }

        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry =
                level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

        boolean placedAny = false;

        if (random.nextInt(16) == 0) { // 1/16 chance
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_FOREST_VEGETATION_BONEMEAL,
                    level, chunkGenerator, random, placePos);
        }
        if (random.nextInt(6) == 0) { // 1/6 chance
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL,
                    level, chunkGenerator, random, placePos);
        }
        if (random.nextInt(16) == 0) { // 1/16 chance
            placedAny |= place(registry, ModConfiguredFeatures.INDIGO_COILSPROUT_BONEMEAL,
                    level, chunkGenerator, random, placePos);
        }
        if (!placedAny && random.nextBoolean()) {
            place(registry, ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL,
                    level, chunkGenerator, random, placePos);
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
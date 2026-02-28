package net.enorme.NER.block.custom;

import com.mojang.serialization.MapCodec;
import net.enorme.NER.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class IndigoNyliumBlock extends Block implements BonemealableBlock {
    public static final MapCodec<IndigoNyliumBlock> CODEC = simpleCodec(IndigoNyliumBlock::new);

    public IndigoNyliumBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<IndigoNyliumBlock> codec() {
        return CODEC;
    }


    private static boolean canBeNylium(LevelReader reader, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = reader.getBlockState(above);
        return !aboveState.canOcclude();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeNylium(level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        BlockState above = level.getBlockState(pos.above());
        return !above.canOcclude();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return isValidBonemealTarget(level, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();

        if (level.getBlockState(abovePos).canOcclude()) {
            return;
        }

        ChunkGenerator chunkGen = level.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry =
                level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

        place(registry, ModConfiguredFeatures.INDIGO_FOREST_VEGETATION_BONEMEAL,
                level, chunkGen, random, abovePos);

        place(registry, ModConfiguredFeatures.INDIGO_SPROUTS_BONEMEAL,
                level, chunkGen, random, abovePos);

        if (random.nextInt(8) == 0) {
            place(registry, ModConfiguredFeatures.INDIGO_COILSPROUT_BONEMEAL,
                    level, chunkGen, random, abovePos);
        }
    }

    private void place(
            Registry<ConfiguredFeature<?, ?>> registry,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            ServerLevel level,
            ChunkGenerator chunkGen,
            RandomSource random,
            BlockPos pos
    ) {
        registry.getHolder(key)
                .ifPresent(holder -> holder.value().place(level, chunkGen, random, pos));
    }

    @Override
    public BonemealableBlock.Type getType() {
        return BonemealableBlock.Type.NEIGHBOR_SPREADER;
    }
}
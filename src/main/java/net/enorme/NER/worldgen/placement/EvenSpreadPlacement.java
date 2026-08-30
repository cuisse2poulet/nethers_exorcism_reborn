package net.enorme.NER.worldgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.enorme.NER.worldgen.ModPlacementModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class EvenSpreadPlacement extends PlacementModifier {
    private static final int DEFAULT_MAX_LAYERS = 8;

    public static final MapCodec<EvenSpreadPlacement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.intRange(1, 256).fieldOf("spacing").forGetter(placement -> placement.spacing),
            Codec.intRange(0, 255).fieldOf("separation").forGetter(placement -> placement.separation),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("chance", 1.0F).forGetter(placement -> placement.chance),
            Codec.intRange(1, 64).optionalFieldOf("max_layers", DEFAULT_MAX_LAYERS).forGetter(placement -> placement.maxLayers)
    ).apply(instance, EvenSpreadPlacement::new));

    private final int spacing;
    private final int separation;
    private final float chance;
    private final int maxLayers;

    private final int jitter;

    private EvenSpreadPlacement(int spacing, int separation, float chance, int maxLayers) {
        this.spacing = spacing;
        this.separation = separation;
        this.chance = chance;
        this.maxLayers = maxLayers;
        this.jitter = Math.max(1, spacing - separation);
    }

    public static EvenSpreadPlacement of(int spacing, int separation) {
        return new EvenSpreadPlacement(spacing, separation, 1.0F, DEFAULT_MAX_LAYERS);
    }

    public static EvenSpreadPlacement of(int spacing, int separation, float chance) {
        return new EvenSpreadPlacement(spacing, separation, chance, DEFAULT_MAX_LAYERS);
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        int minX = pos.getX();
        int minZ = pos.getZ();
        int maxX = minX + 15;
        int maxZ = minZ + 15;
        long levelSeed = context.getLevel().getSeed();

        Stream.Builder<BlockPos> positions = Stream.builder();

        for (int cellX = Math.floorDiv(minX - this.jitter, this.spacing); cellX <= Math.floorDiv(maxX, this.spacing); cellX++) {
            for (int cellZ = Math.floorDiv(minZ - this.jitter, this.spacing); cellZ <= Math.floorDiv(maxZ, this.spacing); cellZ++) {
                RandomSource cellRandom = RandomSource.create(this.cellSeed(levelSeed, cellX, cellZ));

                int x = cellX * this.spacing + cellRandom.nextInt(this.jitter);
                int z = cellZ * this.spacing + cellRandom.nextInt(this.jitter);
                if (x < minX || x > maxX || z < minZ || z > maxZ) {
                    continue;
                }
                if (this.chance < 1.0F && cellRandom.nextFloat() >= this.chance) {
                    continue;
                }

                this.addGroundPositions(context, positions, x, z);
            }
        }

        return positions.build();
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifiers.EVEN_SPREAD.get();
    }

    private long cellSeed(long levelSeed, int cellX, int cellZ) {
        long hash = levelSeed
                + cellX * 341873128712L
                + cellZ * 132897987541L
                + this.spacing * 1013904223L
                + this.separation * 1664525L;
        hash ^= hash >>> 33;
        hash *= 0xff51afd7ed558ccdL;
        hash ^= hash >>> 33;
        hash *= 0xc4ceb9fe1a85ec53L;
        hash ^= hash >>> 33;
        return hash;
    }

    private void addGroundPositions(PlacementContext context, Stream.Builder<BlockPos> positions, int x, int z) {
        int top = context.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(x, top, z);
        BlockState above = context.getBlockState(cursor);
        int found = 0;

        for (int y = top; y >= context.getMinBuildHeight() + 1 && found < this.maxLayers; y--) {
            cursor.setY(y - 1);
            BlockState below = context.getBlockState(cursor);
            if (isEmpty(above) && !isEmpty(below) && !below.is(Blocks.BEDROCK)) {
                positions.add(new BlockPos(x, y, z));
                found++;
            }
            above = below;
        }
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
    }
}

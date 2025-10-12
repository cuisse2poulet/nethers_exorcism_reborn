package net.enorme.NER.worldgen.tree;

import com.mojang.serialization.Codec;
import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class IndigoTreeFeature extends Feature<TreeConfiguration> {
    public IndigoTreeFeature(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos base = ctx.origin();
        RandomSource random = ctx.random();
        TreeConfiguration config = ctx.config();

        int minHeight = 32, maxHeight = 64;
        int height = minHeight + random.nextInt(maxHeight - minHeight + 1);

        int turns = 2 + random.nextInt(2);
        double twoPi = Math.PI * 2;
        double radius = 2.1;
        double thickness = 1.1; // trunk

        BlockState wart = ModBlocks.INDIGO_WART_BLOCK.get().defaultBlockState();
        BlockState trunk = ModBlocks.INDIGO_STEM.get().defaultBlockState();

        for (int y = 0; y < height; y++) {
            double t = (double)y / (height - 1);

            double angle1 = t * turns * twoPi;
            double x1c = radius * Math.cos(angle1);
            double z1c = radius * Math.sin(angle1);

            double angle2 = angle1 + Math.PI;
            double x2c = radius * Math.cos(angle2);
            double z2c = radius * Math.sin(angle2);

            // Fill a tube for each spiral (makes it thicker and round)
            for (double dx = -thickness; dx <= thickness; dx += 0.5) {
                for (double dz = -thickness; dz <= thickness; dz += 0.5) {
                    if (dx * dx + dz * dz <= thickness * thickness + 0.15) {
                        BlockPos vine1 = base.offset((int)Math.round(x1c + dx), y, (int)Math.round(z1c + dz));
                        BlockPos vine2 = base.offset((int)Math.round(x2c + dx), y, (int)Math.round(z2c + dz));
                        level.setBlock(vine1, trunk, 2);
                        level.setBlock(vine2, trunk, 2);
                    }
                }
            }
        }

        int capHeight = 4;
        int capStart = height - capHeight;
        int capRadius = 5;
        for (int y = capStart; y < height; y++) {
            for (int dx = -capRadius; dx <= capRadius; dx++) {
                for (int dz = -capRadius; dz <= capRadius; dz++) {

                    int i = Math.abs(dx) + Math.abs(dz);
                    if (i <= capRadius + 2) {
                        BlockPos capPos = base.offset(dx, y, dz);
                        level.setBlock(capPos, wart, 2);
                        if (y == capStart && i >= capRadius - 1 && random.nextDouble() < 0.65) {
                            int dripLen = 2 + random.nextInt(3);
                            for (int d = 1; d <= dripLen; d++) {
                                BlockPos dripPos = base.offset(dx, y - d, dz);
                                level.setBlock(dripPos, wart, 2);
                            }
                        }
                        if (y == capStart && i >= capRadius - 3 && random.nextDouble() < 0.20) {
                            int dripLen = 1 + random.nextInt(2);
                            for (int d = 1; d <= dripLen; d++) {
                                BlockPos dripPos = base.offset(dx, y - d, dz);
                                level.setBlock(dripPos, wart, 2);
                            }
                        }
                    }
                }
            }
        }

        int pyramidBaseRadius = 5;

        for (int py = 0; py < pyramidBaseRadius; py++) {
            int layerRadius = pyramidBaseRadius - py;
            int yLevel = height + py;
            for (int dx = -layerRadius; dx < layerRadius; dx++) {
                for (int dz = -layerRadius; dz < layerRadius; dz++) {
                     if (Math.abs(dx) + Math.abs(dz) <= layerRadius + 1) {
                    BlockPos pyramidPos = base.offset(dx, yLevel, dz);
                    level.setBlock(pyramidPos, wart, 2);
                     }
                }
            }
        }

        // Remove the base sapling
        level.setBlock(base, Blocks.AIR.defaultBlockState(), 2);

        return true;
    }
}
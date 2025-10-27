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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IndigoTreeFeature extends Feature<TreeConfiguration> {
    public IndigoTreeFeature(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    // Small, reusable candidate class for positions
        private record BlobCandidate(int tx, int tz, double score, BlockPos pos) {
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos base = ctx.origin();
        RandomSource random = ctx.random();
        TreeConfiguration config = ctx.config();

        int minHeight = 32, maxHeight = 64;
        int height = minHeight + random.nextInt(maxHeight - minHeight + 1);

        int turns = 2 + random.nextInt(4);
        double twoPi = Math.PI * 2;
        double radius = 2.1;
        double thickness = 1.1; // trunk

        BlockState wart = ModBlocks.INDIGO_WART_BLOCK.get().defaultBlockState();
        BlockState trunk = ModBlocks.INDIGO_STEM.get().defaultBlockState();
        BlockState shroomLight = ModBlocks.VERDANT_SHROOMLIGHT.get().defaultBlockState();

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

        int sink = 1 + random.nextInt(3); // 1..3
        for (int d = 1; d <= sink; d++) {
            BlockPos downPos = base.below(d);
            if (!level.isOutsideBuildHeight(downPos) && isReplaceableDirt(level, downPos)) {
                level.setBlock(downPos, trunk, 2);
            }
        }

        // Cap parameters
        int capHeight = 4;
        int capStart = Math.max(0, height - capHeight);
        int capRadius = 5;
        // use helper to place cap + drips (extracted to reduce duplication)
        placeCap(level, base, wart, random, capStart, height, capRadius);

        int pyramidBaseRadius = 5;
        int pyramidLayers = 2;
        for (int py = 0; py < pyramidLayers; py++) {
            int layerRadius = Math.max(0, pyramidBaseRadius - py);
            int yLevel = height + py;
            for (int dx = -layerRadius; dx <= layerRadius; dx++) {
                for (int dz = -layerRadius; dz <= layerRadius; dz++) {
                    if (dx * dx + dz * dz <= layerRadius * layerRadius + 1.0) {
                        BlockPos pyramidPos = base.offset(dx, yLevel, dz);
                        if (!level.isOutsideBuildHeight(pyramidPos) && (level.isEmptyBlock(pyramidPos) || isReplaceableDirt(level, pyramidPos))) {
                            level.setBlock(pyramidPos, wart, 2);
                        }
                    }
                }
            }
        }

        for (int turnIndex = 0; turnIndex < turns; turnIndex++) {
            double midT = (turnIndex + 0.5) / (double) turns;
            int yForBlob = (int)Math.round(midT * (height - 1));
            double baseAngle = midT * turns * twoPi;

            for (int spiral = 0; spiral < 2; spiral++) {
                double angle = baseAngle + (spiral == 0 ? 0.0 : Math.PI);
                double xCenter = radius * Math.cos(angle);
                double zCenter = radius * Math.sin(angle);

                int centerIx = (int)Math.round(xCenter);
                int centerIz = (int)Math.round(zCenter);

                int dirX = (int)Math.signum(xCenter);
                int dirZ = (int)Math.signum(zCenter);
                if (dirX == 0 && dirZ == 0) {
                    dirX = Integer.signum(centerIx);
                    dirZ = Integer.signum(centerIz);
                    if (dirX == 0 && dirZ == 0) dirZ = 1;
                }


                int mainRadius = 1 + random.nextInt(3);
                int rimRadius = 1 + random.nextInt(2);

                List<BlobCandidate> mainCandidates = collectLayerCandidates(base, level, trunk, xCenter, zCenter, centerIx, centerIz, mainRadius, yForBlob, dirX, dirZ);
                List<BlobCandidate> topCandidates = collectLayerCandidates(base, level, trunk, xCenter, zCenter, centerIx, centerIz, rimRadius, yForBlob + 1, dirX, dirZ);
                List<BlobCandidate> bottomCandidates = collectLayerCandidates(base, level, trunk, xCenter, zCenter, centerIx, centerIz, rimRadius, yForBlob - 1, dirX, dirZ);

                List<BlobCandidate> touchingMain = new ArrayList<>();
                List<BlobCandidate> nonTouchingMain = new ArrayList<>();
                partitionTouching(level, trunk, mainCandidates, touchingMain, nonTouchingMain);

                List<BlobCandidate> touchingTop = new ArrayList<>();
                List<BlobCandidate> nonTouchingTop = new ArrayList<>();
                partitionTouching(level, trunk, topCandidates, touchingTop, nonTouchingTop);

                List<BlobCandidate> touchingBottom = new ArrayList<>();
                List<BlobCandidate> nonTouchingBottom = new ArrayList<>();
                partitionTouching(level, trunk, bottomCandidates, touchingBottom, nonTouchingBottom);

                int maxTopTouch = 3;
                int maxBottomTouch = 3;
                int maxMainTouch = 4 + random.nextInt(2); // 4 or 5

                limitTouchingCandidates(level, trunk, base, touchingMain, nonTouchingMain, maxMainTouch, dirX, dirZ, yForBlob);
                limitTouchingCandidates(level, trunk, base, touchingTop, nonTouchingTop, maxTopTouch, dirX, dirZ, yForBlob + 1);
                limitTouchingCandidates(level, trunk, base, touchingBottom, nonTouchingBottom, maxBottomTouch, dirX, dirZ, yForBlob - 1);

                List<BlobCandidate> placedMain = new ArrayList<>();
                placedMain.addAll(touchingMain);
                placedMain.addAll(nonTouchingMain);
                placedMain.sort(Comparator.comparingDouble((BlobCandidate c) -> c.score).reversed());

                List<BlobCandidate> placedTop = new ArrayList<>();
                placedTop.addAll(touchingTop);
                placedTop.addAll(nonTouchingTop);
                placedTop.sort(Comparator.comparingDouble((BlobCandidate c) -> c.score).reversed());

                List<BlobCandidate> placedBottom = new ArrayList<>();
                placedBottom.addAll(touchingBottom);
                placedBottom.addAll(nonTouchingBottom);
                placedBottom.sort(Comparator.comparingDouble((BlobCandidate c) -> c.score).reversed());

                BlobCandidate bestMain = placeCandidates(level, wart, placedMain);
                BlobCandidate bestTop = placeCandidates(level, wart, placedTop);
                BlobCandidate bestBottom = placeCandidates(level, wart, placedBottom);

                BlobCandidate anchor = bestTop != null ? bestTop : (bestMain != null ? bestMain : bestBottom);
                if (anchor != null) {
                    placeShroomlightAttached(level, base, trunk, shroomLight, anchor, xCenter, zCenter);
                }
            }
        }
        level.setBlock(base, Blocks.AIR.defaultBlockState(), 2);

        return true;
    }

    /**
     * Place cap layers and drips (extracted to reduce duplication).
     */
    private void placeCap(WorldGenLevel level, BlockPos base, BlockState wart, RandomSource random, int capStart, int height, int capRadius) {
        for (int y = capStart; y < height; y++) {
            for (int dx = -capRadius; dx <= capRadius; dx++) {
                for (int dz = -capRadius; dz <= capRadius; dz++) {

                    double distSq = dx * dx + dz * dz;
                    if (distSq <= capRadius * capRadius + 2.0) {
                        BlockPos capPos = base.offset(dx, y, dz);
                        if (!level.isOutsideBuildHeight(capPos) && (level.isEmptyBlock(capPos) || isReplaceableDirt(level, capPos))) {
                            level.setBlock(capPos, wart, 2);
                        }
                        if (y == capStart && distSq >= (capRadius - 1) * (capRadius - 1) && random.nextDouble() < 0.65) {
                            int dripLen = 2 + random.nextInt(3);
                            for (int d = 1; d <= dripLen; d++) {
                                BlockPos dripPos = base.offset(dx, y - d, dz);
                                if (!level.isOutsideBuildHeight(dripPos) && (level.isEmptyBlock(dripPos) || isReplaceableDirt(level, dripPos))) {
                                    level.setBlock(dripPos, wart, 2);
                                }
                            }
                        }
                        if (y == capStart && distSq >= (capRadius - 3) * (capRadius - 3) && random.nextDouble() < 0.20) {
                            int dripLen = 1 + random.nextInt(2);
                            for (int d = 1; d <= dripLen; d++) {
                                BlockPos dripPos = base.offset(dx, y - d, dz);
                                if (!level.isOutsideBuildHeight(dripPos) && (level.isEmptyBlock(dripPos) || isReplaceableDirt(level, dripPos))) {
                                    level.setBlock(dripPos, wart, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Collect candidates for a square/rim layer, applying euclidean smoothing and shifting outward by dirX/dirZ.
     */
    private List<BlobCandidate> collectLayerCandidates(BlockPos base, WorldGenLevel level, BlockState trunk,
                                                   double xCenter, double zCenter, int centerIx, int centerIz,
                                                   int layerRadius, int yLayer, int dirX, int dirZ) {
        List<BlobCandidate> candidates = new ArrayList<>();
        for (int ix = centerIx - layerRadius - 1; ix <= centerIx + layerRadius + 1; ix++) {
            for (int iz = centerIz - layerRadius - 1; iz <= centerIz + layerRadius + 1; iz++) {
                int dx = ix - centerIx;
                int dz = iz - centerIz;
                int cheb = Math.max(Math.abs(dx), Math.abs(dz));
                double euclidSq = (ix - xCenter) * (ix - xCenter) + (iz - zCenter) * (iz - zCenter);
                boolean inSquare = cheb <= layerRadius && euclidSq <= (layerRadius * layerRadius + 1.6);
                if (!inSquare) continue;

                int targetX = ix + dirX;
                int targetZ = iz + dirZ;
                BlockPos blobPos = base.offset(targetX, yLayer, targetZ);
                if (level.isOutsideBuildHeight(blobPos)) continue;
                if (isSameBlockAs(blobPos, level, trunk)) continue;
                double dot = targetX * xCenter + targetZ * zCenter;
                candidates.add(new BlobCandidate(targetX, targetZ, dot, blobPos));
            }
        }
        return candidates;
    }

    /**
     * Partition candidate list into touching (adjacent to trunk) and non-touching lists.
     */
    private void partitionTouching(WorldGenLevel level, BlockState trunk, List<BlobCandidate> source,
                                   List<BlobCandidate> touchingOut, List<BlobCandidate> nonTouchingOut) {
        for (BlobCandidate c : source) {
            boolean adjacentToTrunk = false;
            BlockPos check = c.pos;
            BlockPos[] neighbors = new BlockPos[] {
                    check.north(), check.south(), check.east(), check.west(),
                    check.north().east(), check.north().west(), check.south().east(), check.south().west()
            };
            for (BlockPos n : neighbors) {
                if (!level.isOutsideBuildHeight(n) && isSameBlockAs(n, level, trunk)) {
                    adjacentToTrunk = true;
                    break;
                }
            }
            if (adjacentToTrunk) touchingOut.add(c); else nonTouchingOut.add(c);
        }
    }

    /**
     * Limit touching candidates to maxTouch by keeping the most outward ones.
     * Demotes extras into nonTouchingOut, attempting to nudge them one more step outward if possible.
     */
    private void limitTouchingCandidates(WorldGenLevel level, BlockState trunk, BlockPos base,
                                         List<BlobCandidate> touching, List<BlobCandidate> nonTouchingOut,
                                         int maxTouch, int dirX, int dirZ, int yLayer) {
        if (touching.size() <= maxTouch) return;
        touching.sort(Comparator.comparingDouble((BlobCandidate c) -> c.score).reversed());
        List<BlobCandidate> keep = new ArrayList<>(touching.subList(0, maxTouch));
        List<BlobCandidate> remove = new ArrayList<>(touching.subList(maxTouch, touching.size()));
        touching.clear();
        touching.addAll(keep);
        for (BlobCandidate rem : remove) {
            int nx = rem.tx + dirX;
            int nz = rem.tz + dirZ;
            BlockPos nudged = base.offset(nx, yLayer, nz);
            if (!level.isOutsideBuildHeight(nudged) && !isSameBlockAs(nudged, level, trunk)) {
                nonTouchingOut.add(new BlobCandidate(nx, nz, rem.score + 0.1, nudged));
            } else {
                nonTouchingOut.add(rem);
            }
        }
    }

    /**
     * Place blocks for a sorted list of candidates. Returns the first-placed candidate (most outward placed), or null.
     */
    private BlobCandidate placeCandidates(WorldGenLevel level, BlockState wart, List<BlobCandidate> candidates) {
        BlobCandidate best = null;
        for (BlobCandidate c : candidates) {
            if (!level.isOutsideBuildHeight(c.pos) && (level.isEmptyBlock(c.pos) || isReplaceableDirt(level, c.pos))) {
                level.setBlock(c.pos, wart, 2);
                if (best == null) best = c;
            }
        }
        return best;
    }

    /**
     * Attempt to attach a shroomlight to the blob rim near the anchor candidate.
     * Preference order: outward adjacent on same layer, outward+up, on-top, orthogonal outwards, fallback to wart.
     */
    private void placeShroomlightAttached(WorldGenLevel level, BlockPos base, BlockState trunk, BlockState shroomLight,
                                          BlobCandidate anchor, double xCenter, double zCenter) {
        int wartX = anchor.tx;
        int wartZ = anchor.tz;
        int wartDirX = Integer.signum(wartX != 0 ? wartX : (int)Math.round(xCenter));
        int wartDirZ = Integer.signum(wartZ != 0 ? wartZ : (int)Math.round(zCenter));
        if (wartDirX == 0 && wartDirZ == 0) wartDirZ = 1;

        BlockPos[] candidatesPos = new BlockPos[] {
                base.offset(wartX + wartDirX, anchor.pos.getY(), wartZ + wartDirZ), // outward adjacent on anchor layer
                base.offset(wartX + wartDirX, anchor.pos.getY() + 1, wartZ + wartDirZ), // outward + up
                base.offset(wartX, anchor.pos.getY() + 1, wartZ), // on top of wart (center)
                base.offset(wartX + wartDirX, anchor.pos.getY(), wartZ), // outward X
                base.offset(wartX, anchor.pos.getY(), wartZ + wartDirZ), // outward Z
                anchor.pos // fallback: on wart itself
        };

        for (BlockPos candidatePos : candidatesPos) {
            if (candidatePos == null) continue;
            if (level.isOutsideBuildHeight(candidatePos)) continue;
            if (isSameBlockAs(candidatePos, level, trunk)) continue; // never overwrite trunk

            if (level.isEmptyBlock(candidatePos) || isReplaceableDirt(level, candidatePos) || candidatePos.equals(anchor.pos)) {
                level.setBlock(candidatePos, shroomLight, 2);
                return;
            }
        }
    }

    private boolean isReplaceableDirt(WorldGenLevel level, BlockPos pos) {
        BlockState s = level.getBlockState(pos);
        return s.getBlock() == Blocks.GRASS_BLOCK || s.getBlock() == Blocks.DIRT || s.getBlock() == Blocks.COARSE_DIRT || s.getBlock() == Blocks.ROOTED_DIRT;
    }

    private boolean isSameBlockAs(BlockPos pos, WorldGenLevel level, BlockState state) {
        BlockState s = level.getBlockState(pos);
        return s.getBlock() == state.getBlock();
    }
}
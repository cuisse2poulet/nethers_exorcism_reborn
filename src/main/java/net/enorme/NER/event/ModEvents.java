package net.enorme.NER.event;

import net.enorme.NER.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;

@EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onBonemeal(BonemealEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (!state.is(Blocks.NETHERRACK)) {
            return;
        }

        boolean hasIndigoNeighbor = false;
        int radius = 1;
        for (int dx = -radius; dx <= radius && !hasIndigoNeighbor; dx++) {
            for (int dy = -radius; dy <= radius && !hasIndigoNeighbor; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos adjPos = pos.offset(dx, dy, dz);
                    BlockState adjState = level.getBlockState(adjPos);
                    if (adjState.getBlock() == ModBlocks.INDIGO_NYLIUM.get()) {
                        hasIndigoNeighbor = true;
                        break;
                    }
                }
            }
        }

        if (!hasIndigoNeighbor) {
            return;
        }

        level.setBlockAndUpdate(pos, ModBlocks.INDIGO_NYLIUM.get().defaultBlockState());

        if (level instanceof ServerLevel serverLevel) {
            spawnBonemealParticles(serverLevel, pos);
        }

        event.setSuccessful(true);
    }

    private static void spawnBonemealParticles(ServerLevel level, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;

        int i = level.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                x,
                y,
                z,
                8,
                0.3D,
                0.3D,
                0.3D,
                0.0D
        );
    }
}

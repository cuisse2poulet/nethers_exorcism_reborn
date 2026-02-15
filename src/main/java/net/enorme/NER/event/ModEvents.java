package net.enorme.NER.event;

import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.block.custom.IndigoNyliumBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class ModEvents {

        @SubscribeEvent
        public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
            Level level = (Level) event.getLevel();
            if (level.isClientSide) return;

            BlockPos pos = event.getPos();
            var oldState = event.getBlockSnapshot().getCurrentState();
            var newState = event.getPlacedBlock();

            // Check if Netherrack was replaced by Indigo Nylium
            if (oldState.is(Blocks.NETHERRACK)
                    && newState.getBlock() == ModBlocks.INDIGO_NYLIUM.get()) {

                System.out.println("[IndigoNylium] SPREAD: Netherrack at " + pos
                        + " in " + level.dimension().location()
                        + " was converted to Indigo Nylium");
            }
        }
}


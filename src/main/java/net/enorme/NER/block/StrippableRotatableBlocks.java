package net.enorme.NER.block;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class StrippableRotatableBlocks extends RotatedPillarBlock {
    public StrippableRotatableBlocks(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                     ItemAbility itemAbility, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(ModBlocks.INDIGO_STEM)) {
                return ModBlocks.STRIPPED_INDIGO_STEM.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            } else if (state.is(ModBlocks.INDIGO_HYPHAE)) {
                return ModBlocks.STRIPPED_INDIGO_HYPHAE.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}

package net.enorme.NER.item;

import net.enorme.NER.entity.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CustomBucketItem extends Item {

    public CustomBucketItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        HitResult hit = player.pick(5.0D, 1.0F, false);
        if (!(hit instanceof BlockHitResult blockHit)) {
            return InteractionResultHolder.pass(stack);
        }

        var spawnPos = blockHit.getBlockPos().relative(blockHit.getDirection());

        if (level instanceof ServerLevel serverLevel) {
            var entity = ModEntities.SALAMANDER.get().spawn(
                    serverLevel, stack, player, spawnPos,
                    MobSpawnType.BUCKET, true, false
            );

            if (entity instanceof Bucketable bucketable) {
                var tag = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                bucketable.loadFromBucketTag(tag.copyTag());
                bucketable.setFromBucket(true);
            }

            level.playSound(null, spawnPos, SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        if (!player.getAbilities().instabuild) {
            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }

        player.swing(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

}
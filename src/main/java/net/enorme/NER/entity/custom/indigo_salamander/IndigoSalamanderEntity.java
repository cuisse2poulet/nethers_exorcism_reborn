package net.enorme.NER.entity.custom.indigo_salamander;

import net.enorme.NER.block.ModBlocks;
import net.enorme.NER.entity.ModEntities;
import net.enorme.NER.entity.custom.indigo_salamander.goal.NapCycleGoal;
import net.enorme.NER.entity.custom.indigo_salamander.goal.NapTimeGoal;
import net.enorme.NER.entity.custom.indigo_salamander.goal.SalamanderFleePlayerGoal;
import net.enorme.NER.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class IndigoSalamanderEntity extends TamableAnimal implements GeoAnimatable, Bucketable {

    private boolean fromBucket = false;

public IndigoSalamanderEntity(EntityType<? extends TamableAnimal> entityType, Level level){
    super(entityType, level);
}



    private boolean wasSleeping = false;

    private PlayState predicate(AnimationState<IndigoSalamanderEntity> state) {
        float animSpeedValue = state.getAnimatable().walkAnimation.speed();
        boolean sleepingNow = isNapping();

        if (sleepingNow) {
            state.getController().setAnimationSpeed(1.0);
            state.setAnimation(RawAnimation.begin()
                    .then("to_sleep", Animation.LoopType.PLAY_ONCE)
                    .thenLoop("sleep_loop2"));
            wasSleeping = true;
        } else if (wasSleeping) {
            state.getController().setAnimationSpeed(1.0);
            state.setAnimation(RawAnimation.begin()
                    .then("sleep_stop2", Animation.LoopType.PLAY_ONCE));
            wasSleeping = false;
        } else if (isPanicking()) {
            double animSpeed = Mth.clamp(animSpeedValue * 4.0, 1.0, 3.0);
            state.getController().setAnimationSpeed(animSpeed);
            state.setAnimation(RawAnimation.begin().thenLoop("run"));
        } else if (state.isMoving()) {
            double animSpeed = Mth.clamp(animSpeedValue * 4.0, 0.5, 2.0);
            state.getController().setAnimationSpeed(animSpeed);
            state.setAnimation(RawAnimation.begin().thenLoop("walk"));
        } else {
            state.getController().setAnimationSpeed(1.0);
            state.setAnimation(RawAnimation.begin().thenLoop("idle"));
        }

        return PlayState.CONTINUE;
    }
    private BlockPos nestPos = null;

    public void setNestPos(BlockPos pos) {
        this.nestPos = pos;
    }

    public BlockPos getNestPos() {
        return nestPos;
    }

    private static final EntityDataAccessor<Boolean> SLEEPING =
            SynchedEntityData.defineId(IndigoSalamanderEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> PANICKING =
            SynchedEntityData.defineId(IndigoSalamanderEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PANICKING, false);
        builder.define(SLEEPING, false);
    }
    public boolean isNapping() {
        return this.entityData.get(SLEEPING);
    }

    public void setNappingCustom(boolean napping) {
        this.entityData.set(SLEEPING, napping);
    }

    public void startNapping() {
        this.setNappingCustom(true);
        this.refreshDimensions();
    }

    public void stopNapping() {
        this.setNappingCustom(false);
        this.refreshDimensions();
    }


    public void setPanicking(boolean panicking) {
        this.entityData.set(PANICKING, panicking);
    }

    public boolean isPanicking() {
        return this.entityData.get(PANICKING);
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SalamanderFleePlayerGoal(this, 1.6, 4.0));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new TemptGoal(this, 0.65, stack -> stack.is(ModBlocks.INDIGO_FUNGUS.asItem()), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F) {
            @Override
            public boolean canUse() {
                return !isNapping() && super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                return !isNapping() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return !isNapping() && super.canUse();
            }
        });
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return !isNapping() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new NapTimeGoal(this));
        this.goalSelector.addGoal(3, new NapCycleGoal(this));
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (isNapping()) {
            return EntityDimensions.fixed(this.getBbWidth() * 1.5f, this.getBbHeight() * 0.8f);
        }
        return super.getDefaultDimensions(pose);
    }


    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

public static AttributeSupplier.Builder createAttributes(){
    return Animal.createLivingAttributes()
            .add(Attributes.MAX_HEALTH,12d)
            .add(Attributes.MOVEMENT_SPEED,0.25d)
            .add(Attributes.FOLLOW_RANGE,24d);
}
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModBlocks.INDIGO_FUNGUS.asItem());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.SALAMANDER.get().create(serverLevel);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(
                        this,
                        "controller",
                        5,
                        this::predicate
                )
        );
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }

    @Override
    public boolean fromBucket() {
        return this.fromBucket;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.fromBucket = fromBucket;
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, tag -> {
            tag.putInt("Age", this.getAge());
        });
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.INDIGO_SALAMANDER_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }
    @Override

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() == Items.BUCKET && this.isAlive()) {
            this.playSound(this.getPickupSound(), 1.0F, 1.0F);
            ItemStack bucketStack = this.getBucketItemStack();
            this.saveToBucketTag(bucketStack);

            ItemStack resultStack = ItemUtils.createFilledResult(itemstack, player, bucketStack, false);
            player.setItemInHand(hand, resultStack);

            if (!this.level().isClientSide) {
                ((ServerPlayer) player).awardStat(net.minecraft.stats.Stats.ITEM_USED.get(Items.BUCKET));
            }

            this.discard();
            player.swing(hand);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }
}


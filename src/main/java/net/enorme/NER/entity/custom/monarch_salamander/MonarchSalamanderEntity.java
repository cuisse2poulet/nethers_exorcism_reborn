package net.enorme.NER.entity.custom.monarch_salamander;

import net.enorme.NER.entity.custom.monarch_salamander.goal.MonarchNapCycleGoal;
import net.enorme.NER.entity.custom.monarch_salamander.goal.MonarchNapTimeGoal;
import net.enorme.NER.utils.CommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MonarchSalamanderEntity extends Animal implements GeoAnimatable {

    private boolean wasSleeping = false;


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
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
        this.goalSelector.addGoal(2, new MonarchNapTimeGoal(this));
        this.goalSelector.addGoal(3, new MonarchNapCycleGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }


    public MonarchSalamanderEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    private BlockPos nestPos = null;

    public void setNestPos(BlockPos pos) {
        this.nestPos = pos;
    }

    public BlockPos getNestPos() {
        return nestPos;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (isNapping()) {
            return EntityDimensions.fixed(this.getBbWidth() * 1.5f, this.getBbHeight() * 0.8f);
        }
        return super.getDefaultDimensions(pose);
    }

    private static final EntityDataAccessor<Boolean> SLEEPING =
            SynchedEntityData.defineId(MonarchSalamanderEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> HAS_TARGET =
            SynchedEntityData.defineId(MonarchSalamanderEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SLEEPING, false);
        builder.define(HAS_TARGET, false);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);

        if (!this.level().isClientSide) {
            this.entityData.set(HAS_TARGET, target != null);

            var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);

            if (speedAttr != null) {
                speedAttr.removeModifier(CHASE_SPEED_MODIFIER_ID);

                if (target != null) {
                    speedAttr.addTransientModifier(CHASE_SPEED_MODIFIER);
                }
            }
        }
    }

    public boolean hasLiveTarget() {
        return this.entityData.get(HAS_TARGET);
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

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40d)
                .add(Attributes.MOVEMENT_SPEED, 0.15d)
                .add(Attributes.FOLLOW_RANGE, 48d)
                .add(Attributes.ATTACK_DAMAGE, 4d);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean result = super.doHurtTarget(entity);

        if (result) {
            AnimatableManager<?> manager =
                    this.getAnimatableInstanceCache().getManagerForId(this.getId());

            if (manager != null) {
                manager.tryTriggerAnimation("attack");
            }
        }

        return result;
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

        controllers.add(
                new AnimationController<>(
                        this,
                        "attackController",
                        0,
                        state -> PlayState.STOP
                ).triggerableAnim(
                        "attack",
                        RawAnimation.begin()
                                .then("attack1", Animation.LoopType.PLAY_ONCE)
                )
        );
    }

    private static final ResourceLocation CHASE_SPEED_MODIFIER_ID = CommonUtils.resourcePath("chase_speed_boost");

    private static final AttributeModifier CHASE_SPEED_MODIFIER =
            new AttributeModifier(CHASE_SPEED_MODIFIER_ID, 1.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    private PlayState predicate(AnimationState<MonarchSalamanderEntity> state) {
        float animSpeedValue = state.getAnimatable().walkAnimation.speed();
        boolean sleepingNow = isNapping();

        if (sleepingNow) {
            state.getController().setAnimationSpeed(0.5);
            state.setAnimation(RawAnimation.begin()
                    .then("go_sleep", Animation.LoopType.PLAY_ONCE)
                    .thenLoop("sleep_loop"));
            wasSleeping = true;
        } else if (wasSleeping) {
            state.getController().setAnimationSpeed(1.0);
            state.setAnimation(RawAnimation.begin()
                    .then("waking_up", Animation.LoopType.PLAY_ONCE));
            wasSleeping = false;
        } else if (hasLiveTarget() && state.isMoving()) {
            double animSpeed = Mth.clamp(animSpeedValue * 1.5, 1.0, 1.5);
            state.getController().setAnimationSpeed(animSpeed);
            state.setAnimation(RawAnimation.begin().thenLoop("running"));
        } else if (state.isMoving()) {
            double animSpeed = Mth.clamp(animSpeedValue * 4.0, 0.5, 2.0);
            state.getController().setAnimationSpeed(animSpeed);
            state.setAnimation(RawAnimation.begin().thenLoop("walk"));
        }
        else {
            state.getController().setAnimationSpeed(1.0);
            state.setAnimation(RawAnimation.begin().thenLoop("idle"));
        }

        return PlayState.CONTINUE;
    }
}
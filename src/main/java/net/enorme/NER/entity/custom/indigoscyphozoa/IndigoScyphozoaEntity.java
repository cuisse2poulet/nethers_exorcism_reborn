package net.enorme.NER.entity.custom.indigoscyphozoa;

import net.enorme.NER.item.NERItems;
import net.enorme.NER.entity.custom.indigoscyphozoa.goal.*;
import net.enorme.NER.sound.NERSound;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class IndigoScyphozoaEntity extends FlyingMob implements GeoAnimatable {

    private static final EntityDataAccessor<Integer> MOOD =
            SynchedEntityData.defineId(
                    IndigoScyphozoaEntity.class,
                    EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_MOOD =
            SynchedEntityData.defineId(
                    IndigoScyphozoaEntity.class,
                    EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MOOD_TRANSITION_TICKS =
            SynchedEntityData.defineId(
                    IndigoScyphozoaEntity.class,
                    EntityDataSerializers.INT);

    public static final int GROUP_RADIUS = 12;
    public static final int MIN_HOVER_HEIGHT = 1;
    public static final int MAX_HOVER_HEIGHT = 5;
    private static final int SAD_TICKS = 600;
    public static final int MOOD_TRANSITION_LENGTH = 20;

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    private int cryingTicks;
    private int moodTicks;
    private int jellyLayTicks;
    private long jellyReadyAt;

    private static final long JELLY_REGROW_TICKS = 6000L;

    private static final int LEAP_MIN_COOLDOWN = 160;
    private static final int LEAP_RANDOM_COOLDOWN = 240;
    private static final double LEAP_UP_STRENGTH = 0.45D;
    private static final double LEAP_FORWARD_STRENGTH = 0.15D;
    private int leapCooldown = LEAP_MIN_COOLDOWN;

    public enum Mood {
        CALM,
        LOVE,
        HURT,
        ANGRY,
        SAD
    }

    public IndigoScyphozoaEntity(EntityType<? extends FlyingMob> type, Level level) {
        super(type, level);

        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putLong("JellyReadyAt", jellyReadyAt);
        tag.putInt("JellyLayTicks", jellyLayTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        jellyReadyAt = tag.getLong("JellyReadyAt");
        jellyLayTicks = tag.getInt("JellyLayTicks");
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack tool = player.getItemInHand(hand);
        if (!tool.is(Items.SHEARS)) {
            return super.mobInteract(player, hand);
        }

        if (!level().isClientSide && level().getGameTime() >= jellyReadyAt) {
            jellyReadyAt = level().getGameTime() + JELLY_REGROW_TICKS;
            spawnAtLocation(NERItems.GLOWING_JELLY.get());
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            playSound(SoundEvents.SHEEP_SHEAR, 0.8F, 1.1F);
        }

        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MOOD, Mood.CALM.ordinal());
        builder.define(PREVIOUS_MOOD, Mood.CALM.ordinal());
        builder.define(MOOD_TRANSITION_TICKS, 0);
    }

    public Mood getMood() {
        return Mood.values()[entityData.get(MOOD)];
    }

    public void setMood(Mood mood) {
        setMood(mood, 0);
    }

    public void setMood(Mood mood, int ticks) {
        int moodId = mood.ordinal();
        if (entityData.get(MOOD) != moodId) {
            entityData.set(PREVIOUS_MOOD, entityData.get(MOOD));
            entityData.set(MOOD, moodId);
            entityData.set(MOOD_TRANSITION_TICKS, MOOD_TRANSITION_LENGTH);
        }

        if (ticks > 0) {
            this.moodTicks = ticks;
        }
    }

    public Mood getPreviousMood() {
        return Mood.values()[entityData.get(PREVIOUS_MOOD)];
    }

    public int getMoodTransitionTicks() {
        return entityData.get(MOOD_TRANSITION_TICKS);
    }

    public float getMoodTransitionProgress(float partialTick) {
        return 1.0F - Math.clamp(
                (getMoodTransitionTicks() - partialTick) / (float) MOOD_TRANSITION_LENGTH, 0.0F, 1.0F);
    }

    public int getCryingTicks() {
        return cryingTicks;
    }

    public void setCryingTicks(int ticks) {
        this.cryingTicks = ticks;
    }

    public int getJellyLayTicks() {
        return jellyLayTicks;
    }

    public void setJellyLayTicks(int ticks) {
        jellyLayTicks = ticks;
    }

    public void dropGlowingJelly() {
        spawnAtLocation(NERItems.GLOWING_JELLY.get());
    }

    public int getNearbyScyphozoaCount() {
        return level().getEntitiesOfClass(
                IndigoScyphozoaEntity.class,
                getBoundingBox().inflate(GROUP_RADIUS)
        ).size();
    }

    public boolean isGrouped() {
        return getNearbyScyphozoaCount() >= 2;
    }

    public boolean isAlone() {
        return getNearbyScyphozoaCount() == 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && getMoodTransitionTicks() > 0) {
            entityData.set(MOOD_TRANSITION_TICKS, getMoodTransitionTicks() - 1);
        }

        LivingEntity target = getTarget();
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            setTarget(null);
            if (getMood() == Mood.ANGRY) {
                setMood(Mood.CALM);
            }
        }

        if (moodTicks > 0) {
            moodTicks--;

            if (moodTicks <= 0 && cryingTicks <= 0) {
                if (getMood() == Mood.ANGRY) {
                    setTarget(null);
                }
                setMood(Mood.CALM);
            }
        }

        if (!level().isClientSide) {
            tickLeap();
        }
    }

    private void tickLeap() {
        if (--leapCooldown > 0) {
            return;
        }
        leapCooldown = LEAP_MIN_COOLDOWN + random.nextInt(LEAP_RANDOM_COOLDOWN);

        if (getTarget() != null || cryingTicks > 0 || isInWaterOrBubble()
                || !level().noCollision(this, getBoundingBox().move(0, 2.0D, 0))) {
            return;
        }

        float yawRad = getYRot() * Mth.DEG_TO_RAD;
        double forwardX = -Mth.sin(yawRad) * LEAP_FORWARD_STRENGTH;
        double forwardZ = Mth.cos(yawRad) * LEAP_FORWARD_STRENGTH;

        setDeltaMovement(getDeltaMovement().add(forwardX, LEAP_UP_STRENGTH, forwardZ));
        hasImpulse = true;
        playSound(NERSound.SCYPHOZOA_MOVE.get(), 0.8F, 0.9F + random.nextFloat() * 0.3F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 1.4)
                .add(Attributes.FLYING_SPEED, 1.4)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        goalSelector.addGoal(0, new MaintainAltitudeGoal(this));
        goalSelector.addGoal(1, new FleeWhenAloneGoal(this));
        goalSelector.addGoal(2, new HallucinationDefenseGoal(this));
        goalSelector.addGoal(3, new HomingInGoal(this));
        goalSelector.addGoal(4, new CryingGoal(this));
        goalSelector.addGoal(5, new LowFlyingWanderGoal(this));
        goalSelector.addGoal(6, new GroupJellyLayingGoal(this));
        goalSelector.addGoal(7, new LonelyMoodGoal(this));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);

        if (!result)
            return false;

        Entity attacker = source.getEntity();

        setCryingTicks(0);
        setMood(Mood.HURT, 40);

        if (attacker instanceof LivingEntity living && isGrouped()) {

            for (IndigoScyphozoaEntity jelly :
                    level().getEntitiesOfClass(
                            IndigoScyphozoaEntity.class,
                            getBoundingBox().inflate(GROUP_RADIUS))) {

                if (jelly == this) {
                    continue;
                }
                jelly.setMood(Mood.ANGRY, 200);
                jelly.setTarget(living);
            }
        }

        return true;
    }

    @Override
    public void die(DamageSource source) {

        if (!level().isClientSide) {

            for (IndigoScyphozoaEntity jelly :
                    level().getEntitiesOfClass(
                            IndigoScyphozoaEntity.class,
                            getBoundingBox().inflate(16))) {

                if (jelly != this) {
                    if (jelly.getTarget() == null && jelly.getMood() != Mood.ANGRY) {
                        jelly.setMood(Mood.SAD, SAD_TICKS);
                        jelly.setCryingTicks(SAD_TICKS);
                    }
                }
            }
        }

        super.die(source);
    }

    private PlayState predicate(AnimationState<IndigoScyphozoaEntity> state) {

        if (this.swinging || (getMood() == Mood.ANGRY && getTarget() != null)) {
            state.setAnimation(
                    RawAnimation.begin()
                            .thenPlay("attack"));

            return PlayState.CONTINUE;
        }

        state.setAnimation(
                RawAnimation.begin()
                        .thenLoop(state.isMoving() ? "MOVEMENT" : "idle"));

        return PlayState.CONTINUE;
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


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return NERSound.SCYPHOZOA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return NERSound.SCYPHOZOA_IDLE.get();
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return NERSound.SCYPHOZOA_DEATH.get();
    }
    @Override
    public double getTick(Object object) {
        return tickCount;
    }
}

package net.enorme.NER.entity.custom.indigoscyphozoa;

import net.enorme.NER.entity.custom.indigoscyphozoa.goal.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class IndigoScyphozoaEntity extends FlyingMob implements GeoAnimatable {

    private static final EntityDataAccessor<Integer> MOOD =
            SynchedEntityData.defineId(
                    IndigoScyphozoaEntity.class,
                    EntityDataSerializers.INT);

    public static final int GROUP_RADIUS = 12;
    public static final int MIN_HOVER_HEIGHT = 1;
    public static final int MAX_HOVER_HEIGHT = 5;
    private static final int SAD_TICKS = 600;
    private static final int LONELY_TICKS_BEFORE_SAD = 400;

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    private int cryingTicks;
    private int moodTicks;
    private int lonelyTicks;
    private boolean wasGrouped;

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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MOOD, Mood.CALM.ordinal());
    }

    public Mood getMood() {
        return Mood.values()[entityData.get(MOOD)];
    }

    public void setMood(Mood mood) {
        setMood(mood, 0);
    }

    public void setMood(Mood mood, int ticks) {
        entityData.set(MOOD, mood.ordinal());

        if (ticks > 0) {
            this.moodTicks = ticks;
        }
    }

    public int getCryingTicks() {
        return cryingTicks;
    }

    public void setCryingTicks(int ticks) {
        this.cryingTicks = ticks;
    }

    public boolean isGrouped() {
        return level().getEntitiesOfClass(
                IndigoScyphozoaEntity.class,
                getBoundingBox().inflate(GROUP_RADIUS)
        ).size() >= 2;
    }

    public boolean isAlone() {
        return level().getEntitiesOfClass(
                IndigoScyphozoaEntity.class,
                getBoundingBox().inflate(GROUP_RADIUS)
        ).size() == 1;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = getTarget();
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            setTarget(null);
            if (getMood() == Mood.ANGRY) {
                setMood(Mood.CALM);
            }
        }

        updateLonelyMood();

        if (moodTicks > 0) {
            moodTicks--;

            if (moodTicks <= 0 && cryingTicks <= 0) {
                if (getMood() == Mood.ANGRY) {
                    setTarget(null);
                }
                setMood(Mood.CALM);
            }
        }
    }

    private void updateLonelyMood() {
        if (isGrouped()) {
            wasGrouped = true;
            lonelyTicks = 0;
            return;
        }

        if (!wasGrouped || getTarget() != null || getMood() == Mood.ANGRY || getMood() == Mood.HURT) {
            return;
        }

        lonelyTicks++;
        if (lonelyTicks >= LONELY_TICKS_BEFORE_SAD) {
            setMood(Mood.SAD, SAD_TICKS);
            setCryingTicks(SAD_TICKS);
            wasGrouped = false;
            lonelyTicks = 0;
        }
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

    @Override
    public double getTick(Object object) {
        return tickCount;
    }
}

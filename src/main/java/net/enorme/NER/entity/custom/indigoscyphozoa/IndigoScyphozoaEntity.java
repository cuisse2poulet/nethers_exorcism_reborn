package net.enorme.NER.entity.custom.indigoscyphozoa;

import net.enorme.NER.entity.custom.indigoscyphozoa.goal.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public class IndigoScyphozoaEntity extends FlyingMob {

    public enum Mood {
        CALM,
        LOVE,
        HURT,
        ANGRY,
        SAD
    }

    private static final EntityDataAccessor<Integer> MOOD =
            SynchedEntityData.defineId(IndigoScyphozoaEntity.class, EntityDataSerializers.INT);

    public static final int MIN_GROUP_SIZE = 1;
    public static final int MAX_GROUP_SIZE = 5;

    public static final int GROUP_RADIUS = 12;

    public static final int MIN_HOVER_HEIGHT = 1;
    public static final int MAX_HOVER_HEIGHT = 5;

    public static final int CRYING_RADIUS = 16;

    private int cryingTicks;

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
        entityData.set(MOOD, mood.ordinal());
    }

    public int getMourningTicks() {
        return cryingTicks;
    }

    public void setMourningTicks(int mourningTicks) {
        this.cryingTicks = mourningTicks;
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

        targetSelector.addGoal(1, new GroupDefendGoal(this));
    }
}
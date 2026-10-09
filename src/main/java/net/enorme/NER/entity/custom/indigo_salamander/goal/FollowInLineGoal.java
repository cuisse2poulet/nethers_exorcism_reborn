package net.enorme.NER.entity.custom.indigo_salamander.goal;

import net.enorme.NER.entity.custom.indigo_salamander.ChainFollower;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class FollowInLineGoal<T extends PathfinderMob & ChainFollower> extends Goal {
    private final T mob;
    private final Class<? extends LivingEntity> leaderClass;
    private final double speed;
    private final float gap;        // distance kept behind the one in front
    private final float searchRadius;
    private int recalcTimer;
    private Vec3 lastTargetPos;
    private int stillTicks;

    public FollowInLineGoal(T mob, Class<? extends LivingEntity> leaderClass,
                            double speed, float gap, float searchRadius) {
        this.mob = mob;
        this.leaderClass = leaderClass;
        this.speed = speed;
        this.gap = gap;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private Vec3 spotBehind(LivingEntity t) {
        float yaw = t.yBodyRot * Mth.DEG_TO_RAD;
        Vec3 forward = new Vec3(-Mth.sin(yaw), 0, Mth.cos(yaw));
        return t.position().subtract(forward.scale(gap));
    }

    private boolean targetValid() {
        LivingEntity t = mob.getChainTarget();
        return t != null && t.isAlive()
                && mob.distanceToSqr(t) < (searchRadius * 2) * (searchRadius * 2);
    }

    @Override
    public boolean canUse() {
        if (!targetValid()) {
            mob.setChainTarget(null);
            mob.setChainTarget(findSlot());
        }
        LivingEntity t = mob.getChainTarget();
        return t != null && mob.position().distanceToSqr(spotBehind(t)) > 1.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return targetValid()
                && mob.position().distanceToSqr(spotBehind(mob.getChainTarget())) > 1.0D;
    }

    @Override
    public void start() { recalcTimer = 0; }

    @Override
    public void stop() { mob.getNavigation().stop(); }

    @Override
    public void tick() {
        LivingEntity t = mob.getChainTarget();
        mob.getLookControl().setLookAt(t, 10.0F, mob.getMaxHeadXRot());

        // Track whether the one in front is moving
        Vec3 now = t.position();
        if (lastTargetPos != null && now.distanceToSqr(lastTargetPos) < 0.0001D) {
            stillTicks++;
        } else {
            stillTicks = 0;
        }
        lastTargetPos = now;

        // Target has stopped and we're close enough: freeze in place
        if (stillTicks > 10 && mob.distanceToSqr(t) < (gap * 3) * (gap * 3)) {
            mob.getNavigation().stop();
            return;
        }

        if (--recalcTimer <= 0) {
            recalcTimer = adjustedTickDelay(5);
            Vec3 p = spotBehind(t);
            double s = mob.distanceToSqr(t) > 36 ? speed * 1.4 : speed;
            mob.getNavigation().moveTo(p.x, p.y, p.z, s);
        }
    }
    @Nullable
    private LivingEntity findSlot() {
        AABB box = mob.getBoundingBox().inflate(searchRadius);

        List<? extends LivingEntity> leaders =
                mob.level().getEntitiesOfClass(leaderClass, box, LivingEntity::isAlive);

        List<LivingEntity> followers = new ArrayList<>();
        for (Entity e : mob.level().getEntities(mob, box, e -> e.getClass() == mob.getClass() && e.isAlive())) {
            if (e instanceof ChainFollower cf && cf.getChainTarget() != null) {
                followers.add((LivingEntity) e); // only followers already in a chain
            }
        }

        List<LivingEntity> candidates = new ArrayList<>(leaders);
        candidates.addAll(followers);

        return candidates.stream()
                .filter(c -> c != mob)
                .filter(c -> !isFollowedByAnother(c, box))
                .filter(c -> !isDownstream(c))
                .min(Comparator.comparingDouble(mob::distanceToSqr))
                .orElse(null);
    }

    private boolean isFollowedByAnother(LivingEntity candidate, AABB box) {
        return !mob.level().getEntities(mob, box.inflate(searchRadius),
                e -> e instanceof ChainFollower cf && cf.getChainTarget() == candidate).isEmpty();
    }


    private boolean isDownstream(LivingEntity candidate) {
        LivingEntity cur = candidate;
        for (int i = 0; i < 64 && cur instanceof ChainFollower cf; i++) {
            cur = cf.getChainTarget();
            if (cur == mob) return true;
        }
        return false;
    }
}
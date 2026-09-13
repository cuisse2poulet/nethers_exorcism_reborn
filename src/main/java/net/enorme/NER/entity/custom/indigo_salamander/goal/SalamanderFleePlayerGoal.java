package net.enorme.NER.entity.custom.indigo_salamander.goal;

import net.enorme.NER.entity.custom.indigo_salamander.IndigoSalamanderEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SalamanderFleePlayerGoal extends Goal {

    private final IndigoSalamanderEntity salamander;
    private final double speedModifier;
    private final double detectionRange;

    private Player scaryPlayer;
    private Vec3 fleeDirection;

    public SalamanderFleePlayerGoal(IndigoSalamanderEntity mob, double speedModifier, double detectionRange) {
        this.salamander = mob;
        this.speedModifier = speedModifier;
        this.detectionRange = detectionRange;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    private Vec3 getFleeDirection()
    {
        Vec3 fleeVec = DefaultRandomPos.getPosAway(
                salamander,
                16, 7,
                scaryPlayer.position()
        );

        return fleeVec;
    }

    @Override
    public boolean canUse() {
        scaryPlayer = salamander.level().getNearestPlayer(salamander, detectionRange);

        DamageSource dmg = salamander.getLastDamageSource();
        boolean isAttacking = false;
        if(dmg != null){
            isAttacking = scaryPlayer == dmg.getEntity();
        }

        if (scaryPlayer == null) return false;
        if (scaryPlayer.isSpectator() || scaryPlayer.isCreative()) return false;

        if(isAttacking)
        {
            Vec3 f = getFleeDirection();
            if(f == null) return false;
            else fleeDirection = f;
        }
        else {
            if (scaryPlayer.isShiftKeyDown()) return false;

            Vec3 f = getFleeDirection();
            if(f == null) return false;
            else fleeDirection = f;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !salamander.getNavigation().isDone();
    }

    @Override
    public void start() {
        salamander.getNavigation().moveTo(fleeDirection.x, fleeDirection.y, fleeDirection.z, speedModifier);
        salamander.setPanicking(true);
    }

    @Override
    public void stop() {
        salamander.setPanicking(false);
        scaryPlayer = null;
    }
}
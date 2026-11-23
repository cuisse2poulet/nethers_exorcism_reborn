package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class LonelyMoodGoal extends Goal {
    private static final int SAD_TICKS = 600;
    private static final int LONELY_TICKS_BEFORE_SAD = 400;

    private final IndigoScyphozoaEntity jelly;
    private int lonelyTicks;
    private boolean sadFromLoneliness;

    public LonelyMoodGoal(IndigoScyphozoaEntity jelly) {
        this.jelly = jelly;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (jelly.isGrouped()) {
            lonelyTicks = 0;
            clearSadMood();
            return;
        }

        IndigoScyphozoaEntity.Mood mood = jelly.getMood();
        if (jelly.getTarget() != null
                || mood == IndigoScyphozoaEntity.Mood.ANGRY
                || mood == IndigoScyphozoaEntity.Mood.HURT) {
            lonelyTicks = 0;
            sadFromLoneliness = false;
            return;
        }

        if (++lonelyTicks >= LONELY_TICKS_BEFORE_SAD) {
            jelly.setMood(IndigoScyphozoaEntity.Mood.SAD, SAD_TICKS);
            jelly.setCryingTicks(SAD_TICKS);
            sadFromLoneliness = true;
        }
    }

    private void clearSadMood() {
        if (sadFromLoneliness && jelly.getMood() == IndigoScyphozoaEntity.Mood.SAD) {
            jelly.setCryingTicks(0);
            jelly.setMood(IndigoScyphozoaEntity.Mood.CALM);
        }
        sadFromLoneliness = false;
    }
}

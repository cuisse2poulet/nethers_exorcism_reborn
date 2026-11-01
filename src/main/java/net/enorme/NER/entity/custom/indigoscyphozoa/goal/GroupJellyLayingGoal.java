package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class GroupJellyLayingGoal extends Goal {
    private static final int MIN_GROUP_SIZE = 4;
    private static final int MIN_LAY_TICKS = 6000;
    private static final int MAX_LAY_TICKS = 12000;

    private final IndigoScyphozoaEntity jelly;

    public GroupJellyLayingGoal(IndigoScyphozoaEntity jelly) {
        this.jelly = jelly;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        boolean largeGroup = jelly.getNearbyScyphozoaCount() >= MIN_GROUP_SIZE;
        if (largeGroup && jelly.getMood() == IndigoScyphozoaEntity.Mood.CALM) {
            jelly.setMood(IndigoScyphozoaEntity.Mood.LOVE);
        } else if (!largeGroup && jelly.getMood() == IndigoScyphozoaEntity.Mood.LOVE) {
            jelly.setMood(IndigoScyphozoaEntity.Mood.CALM);
        }

        if (!largeGroup || jelly.getMood() != IndigoScyphozoaEntity.Mood.LOVE) {
            return;
        }

        int layTicks = jelly.getJellyLayTicks();
        if (layTicks <= 0) {
            layTicks = nextLayInterval();
        }
        if (--layTicks <= 0) {
            jelly.dropGlowingJelly();
            layTicks = nextLayInterval();
        }
        jelly.setJellyLayTicks(layTicks);
    }

    private int nextLayInterval() {
        return MIN_LAY_TICKS + jelly.getRandom().nextInt(MAX_LAY_TICKS - MIN_LAY_TICKS + 1);
    }
}

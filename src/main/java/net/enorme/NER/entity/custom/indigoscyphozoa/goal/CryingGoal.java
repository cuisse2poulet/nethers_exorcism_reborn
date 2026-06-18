package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class CryingGoal extends Goal {
        private final IndigoScyphozoaEntity mob;

        public CryingGoal(IndigoScyphozoaEntity mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            return mob.getCryingTicks() > 0;
        }

        @Override
        public void tick() {

            mob.setMood(IndigoScyphozoaEntity.Mood.SAD);

            mob.setCryingTicks(
                    mob.getCryingTicks() - 1);

            if (mob.getCryingTicks() <= 0) {
                mob.setMood(IndigoScyphozoaEntity.Mood.CALM);
            }
        }
}
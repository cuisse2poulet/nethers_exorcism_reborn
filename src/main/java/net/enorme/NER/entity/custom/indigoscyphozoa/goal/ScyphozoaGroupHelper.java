package net.enorme.NER.entity.custom.indigoscyphozoa.goal;

import net.enorme.NER.entity.custom.indigoscyphozoa.IndigoScyphozoaEntity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class ScyphozoaGroupHelper {

    public static List<IndigoScyphozoaEntity> getGroup(
            IndigoScyphozoaEntity entity) {

        return entity.level().getEntitiesOfClass(
                IndigoScyphozoaEntity.class,
                entity.getBoundingBox().inflate(
                        IndigoScyphozoaEntity.GROUP_RADIUS));
    }

    public static int getGroupSize(
            IndigoScyphozoaEntity entity) {

        return getGroup(entity).size();
    }

    public static boolean isGrouped(
            IndigoScyphozoaEntity entity) {

        return getGroupSize(entity) >= 2;
    }

    public static void alertGroup(
            IndigoScyphozoaEntity entity,
            LivingEntity attacker) {

        for (IndigoScyphozoaEntity jelly : getGroup(entity)) {

            jelly.setTarget(attacker);
            jelly.setMood(IndigoScyphozoaEntity.Mood.ANGRY);
        }
    }
}
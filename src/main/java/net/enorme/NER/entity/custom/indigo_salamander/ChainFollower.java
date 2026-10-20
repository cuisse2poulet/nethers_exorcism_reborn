package net.enorme.NER.entity.custom.indigo_salamander;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public interface ChainFollower {
    @Nullable LivingEntity getChainTarget();
        void setChainTarget(@Nullable LivingEntity target);

}

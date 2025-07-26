package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MooshroomEntity;

public class BrownMooshroomSpawnEgg extends GeneralCustomSpawnEgg {
    public BrownMooshroomSpawnEgg(Settings settings) {
        super(EntityType.MOOSHROOM, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof MooshroomEntity mooshroom) {
            mooshroom.setVariant(MooshroomEntity.Type.BROWN);
        }
    }
}

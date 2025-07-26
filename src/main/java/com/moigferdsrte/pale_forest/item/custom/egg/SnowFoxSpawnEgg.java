package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FoxEntity;

public class SnowFoxSpawnEgg extends GeneralCustomSpawnEgg{


    public SnowFoxSpawnEgg(Settings settings) {
        super(EntityType.FOX, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof FoxEntity fox) {
            fox.setVariant(FoxEntity.Type.SNOW);
        }
    }
}

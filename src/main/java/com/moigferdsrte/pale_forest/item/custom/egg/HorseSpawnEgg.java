package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;

public class HorseSpawnEgg extends GeneralCustomSpawnEgg{

    private final HorseColor color;

    public HorseSpawnEgg(HorseColor color, Settings settings) {
        super(EntityType.HORSE, settings);
        this.color = color;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof HorseEntity horse) {
            horse.setVariant(color);
        }
    }
}

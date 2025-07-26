package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;

import java.util.Optional;

public class ShulkerDyeableSpawnEgg extends GeneralCustomSpawnEgg {

    private final DyeColor color;

    public ShulkerDyeableSpawnEgg(DyeColor color, Settings settings) {
        super(EntityType.SHULKER, settings);
        this.color = color;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof ShulkerEntity shulkerEntity) {
            shulkerEntity.setVariant(Optional.ofNullable(this.color).or(shulkerEntity::getVariant));
        }
    }
}

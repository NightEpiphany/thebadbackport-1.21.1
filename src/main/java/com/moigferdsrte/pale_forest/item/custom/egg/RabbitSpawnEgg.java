package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.text.Text;

public class RabbitSpawnEgg extends GeneralCustomSpawnEgg{

    private final RabbitEntity.RabbitType variants;

    private final boolean isToast;

    public RabbitSpawnEgg(RabbitEntity.RabbitType variants, Settings settings, boolean isToast) {
        super(EntityType.RABBIT, settings);
        this.variants = variants;
        this.isToast = isToast;
    }

    @Override
    public void operation(MobEntity entity) {
        if (this.isToast) {
            entity.setCustomName(Text.of("Toast"));
            return;
        }
        if (entity instanceof RabbitEntity rabbit) {
            rabbit.setVariant(this.variants);
        }
    }
}

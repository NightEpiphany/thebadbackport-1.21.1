package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ParrotEntity;

public class ParrotSpawnEgg extends GeneralCustomSpawnEgg{

    private final ParrotEntity.Variant variants;

    public ParrotSpawnEgg(ParrotEntity.Variant variants, Settings settings) {
        super(EntityType.PARROT, settings);
        this.variants = variants;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof ParrotEntity parrot) {
            parrot.setVariant(this.variants);
        }
    }
}

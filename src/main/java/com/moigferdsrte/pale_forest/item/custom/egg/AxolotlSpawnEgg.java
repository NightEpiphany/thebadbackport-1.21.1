package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlEntity;

public class AxolotlSpawnEgg extends GeneralCustomSpawnEgg {

    private final AxolotlEntity.Variant variant;

    public AxolotlSpawnEgg(AxolotlEntity.Variant variant, Settings settings) {
        super(EntityType.AXOLOTL, settings);
        this.variant = variant;
    }


    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof AxolotlEntity axolotl) {
            axolotl.setVariant(this.variant);
        }
    }
}

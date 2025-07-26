package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GoatEntity;

public class ScreamingGoatSpawnEgg extends GeneralCustomSpawnEgg {
    public ScreamingGoatSpawnEgg(Settings settings) {
        super(EntityType.GOAT, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof GoatEntity goat) {
            goat.setScreaming(true);
        }
    }
}

package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public class PreviewMobSpawnEgg extends GeneralCustomSpawnEgg {
    public PreviewMobSpawnEgg(EntityType<? extends MobEntity> type, Settings settings) {
        super(type, settings);
    }

    @Override
    public void operation(MobEntity entity) {

    }

    public enum BiomeIdentifier {
        WARM("warm"),
        COLD("cold");

        private final String name;

        private BiomeIdentifier(String variantString) {
            this.name = variantString;
        }

        public String getName() {
            return name;
        }
    }
}

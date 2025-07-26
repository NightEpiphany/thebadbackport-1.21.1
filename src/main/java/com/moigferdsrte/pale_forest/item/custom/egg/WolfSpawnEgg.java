package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public class WolfSpawnEgg extends GeneralCustomSpawnEgg{

    public WolfSpawnEgg(WolfIdentifiers wolfVariant, Settings settings) {
        super(EntityType.WOLF, settings);
    }

    @Override
    public void operation(MobEntity entity) {

    }

    public enum WolfIdentifiers {
        PALE("wolf"),
        SPOTTED("spotted"),
        SNOWY("snowy"),
        BLACK("black"),
        ASHEN("ashen"),
        RUSTY("rusty"),
        WOODS("woods"),
        CHESTNUT("chestnut"),
        STRIPED("striped");


        private final String name;

        private WolfIdentifiers(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


}

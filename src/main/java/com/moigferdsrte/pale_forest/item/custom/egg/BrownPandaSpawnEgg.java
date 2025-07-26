package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PandaEntity;

public class BrownPandaSpawnEgg extends GeneralCustomSpawnEgg{
    public BrownPandaSpawnEgg(Settings settings) {
        super(EntityType.PANDA, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof PandaEntity panda) {
            panda.setMainGene(PandaEntity.Gene.BROWN);
            panda.setHiddenGene(PandaEntity.Gene.BROWN);
        }
    }
}

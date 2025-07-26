package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.LlamaEntity;

public class LlamaSpawnEgg extends GeneralCustomSpawnEgg{

    private final LlamaEntity.Variant variants;

    public LlamaSpawnEgg(EntityType<? extends MobEntity> type, LlamaEntity.Variant variants, Settings settings) {
        super(type, settings);
        this.variants = variants;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof LlamaEntity llama){
            llama.setVariant(this.variants);
        }
    }
}

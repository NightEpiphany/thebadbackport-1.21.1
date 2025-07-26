package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;

public class ShearedEntitySpawnEgg extends GeneralCustomSpawnEgg{

    public ShearedEntitySpawnEgg(EntityType<? extends MobEntity> type, Settings settings) {
        super(type, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof Shearable) {
            NbtCompound nbt = new NbtCompound();
            entity.writeNbt(nbt);
            nbt.putBoolean("sheared", true);
            nbt.putBoolean("Pumpkin", false);
            entity.readNbt(nbt);
        }
    }
}

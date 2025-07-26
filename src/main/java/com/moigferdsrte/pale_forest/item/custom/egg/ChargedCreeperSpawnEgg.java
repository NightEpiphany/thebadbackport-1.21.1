package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;

public class ChargedCreeperSpawnEgg extends GeneralCustomSpawnEgg {
    public ChargedCreeperSpawnEgg(Settings settings) {
        super(EntityType.CREEPER, settings);
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof CreeperEntity) {
            NbtCompound nbt = new NbtCompound();
            entity.writeNbt(nbt);
            nbt.putBoolean("powered", true);
            entity.readNbt(nbt);
        }
    }
}

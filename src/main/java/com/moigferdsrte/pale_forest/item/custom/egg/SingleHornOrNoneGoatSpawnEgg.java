package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.nbt.NbtCompound;

public class SingleHornOrNoneGoatSpawnEgg extends GeneralCustomSpawnEgg{

    private final GoatHornCondition isHorn;

    public SingleHornOrNoneGoatSpawnEgg(GoatHornCondition isHorn, Settings settings) {
        super(EntityType.GOAT, settings);
        this.isHorn = isHorn;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof GoatEntity goat) {
            if (this.isHorn != GoatHornCondition.NONE) {
                NbtCompound nbt = new NbtCompound();
                goat.writeNbt(nbt);
                nbt.putBoolean("HasLeftHorn", this.isHorn == GoatHornCondition.LEFT);
                nbt.putBoolean("HasRightHorn", this.isHorn == GoatHornCondition.RIGHT);
                goat.readNbt(nbt);
            }else {
                goat.removeHorns();
            }
        }
    }

    public enum GoatHornCondition{
        LEFT("left"),
        RIGHT("right"),
        NONE("no");

        private final String name;

        private GoatHornCondition(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

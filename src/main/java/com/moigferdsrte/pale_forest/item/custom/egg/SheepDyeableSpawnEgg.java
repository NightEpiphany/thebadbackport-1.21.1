package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

public class SheepDyeableSpawnEgg extends GeneralCustomSpawnEgg {
    private final DyeColor color;

    private final boolean isJeb;

    public SheepDyeableSpawnEgg(DyeColor color, Settings settings, boolean isJeb) {
        super(EntityType.SHEEP, settings);
        this.color = color;
        this.isJeb = isJeb;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof SheepEntity sheep) {
            sheep.setColor(this.color);
            if (this.isJeb) {
                sheep.setCustomName(Text.of("jeb_"));
            }
        }
    }
}

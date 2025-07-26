package com.moigferdsrte.pale_forest.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;

public class HarnessItem extends Item {

    private final DyeColor dyeColor;

    private final boolean isForFestival;

    public HarnessItem(DyeColor dyeColor, boolean isForFestival, Settings settings) {
        super(settings);
        this.isForFestival = isForFestival;
        this.dyeColor = dyeColor;
    }

    public boolean isForFestival() {
        return isForFestival;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return super.useOnEntity(stack, user, entity, hand);
    }
}

package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.item.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChickenEntity.class)
public abstract class ChickenLayEggMixin {

    @Shadow
    public int eggLayTime = 4500;

    @Redirect(method = "tickMovement",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/ChickenEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;")
    )private ItemEntity layEgg(ChickenEntity instance, ItemConvertible item) {
        World world = instance.getWorld();
        BlockPos pos = instance.getBlockPos();

        Biome biome = world.getBiome(pos).value();

        if (biome.isCold(pos)) {
            instance.dropItem(ModItems.BLUE_EGG);
        } else if (biome.getTemperature() > 1.0F) {
            instance.dropItem(ModItems.BROWN_EGG);
        }else {
            instance.dropItem(item);
        }
        this.eggLayTime = world.random.nextInt(125) + 4500;
        return new ItemEntity(
                world,
                instance.getX(),
                instance.getY(),
                instance.getZ(),
                new ItemStack(Items.FEATHER, 1)
        );
    }
}

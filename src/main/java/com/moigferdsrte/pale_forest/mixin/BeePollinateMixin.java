package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.tags.ModTags;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public class BeePollinateMixin {
    @Inject(method = "isFlowers", at = @At("HEAD"))
    public void setUniqueFlower(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        World world = ((BeeEntity) (Object) this).getWorld();
        if (world.getBlockState(pos).isIn(ModTags.EYEBLOSSOM) && !((BeeEntity) (Object) this).hasStatusEffect(StatusEffects.POISON)){
            ((BeeEntity) (Object) this).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 180, 2));
        }
    }
}


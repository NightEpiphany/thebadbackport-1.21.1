package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TntBlock.class)
public class TNTExplodeMixin {

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void use(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION)&& !world.isClient()) {
            player.sendMessage(Text.translatable("block.pale_forest.tnt.disabled"), true);
            cir.setReturnValue(ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
    }

    @Inject(method = "onBlockAdded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isReceivingRedstonePower(Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void pre(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION)&& !world.isClient()) {
            ci.cancel();
        }
    }

    @Inject(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isReceivingRedstonePower(Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void pre(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION)&& !world.isClient()) {
            ci.cancel();
        }
    }

    @Inject(method = "primeTnt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private static void pre(World world, BlockPos pos, LivingEntity igniter, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION) && !world.isClient()) {
            ItemEntity itemEntity = new ItemEntity(
                    world,
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.1,
                    (double)pos.getZ() + 0.5,
                    new ItemStack(Items.TNT, 1)
            );
            world.spawnEntity(itemEntity);
            ci.cancel();
        }
    }

    @Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
    private void pre(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION)&& !world.isClient()) {
            ci.cancel();
        }
    }

    @Inject(method = "onDestroyedByExplosion", at = @At("HEAD"), cancellable = true)
    private void pre(World world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(ThePaleForest.TNT_EXPLOSION)&& !world.isClient()) {
            ci.cancel();
        }
    }
}

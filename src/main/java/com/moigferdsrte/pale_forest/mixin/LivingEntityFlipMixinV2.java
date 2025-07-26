package com.moigferdsrte.pale_forest.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityFlipMixinV2<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {

    @Shadow
    protected M model;

    protected LivingEntityFlipMixinV2(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
        super(ctx);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;setupTransforms(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;FFFF)V")
    )
    private void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci,
                        @Local (ordinal = 7) float n, @Local(ordinal = 0, argsOnly = true) float h, @Local(ordinal = 6) float lx) {
        if (shouldFlip(livingEntity)) {
            this.setupTransforms(livingEntity, matrixStack, n, h, g, lx);
        }
    }

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void label(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (shouldFlip(livingEntity))
            cir.setReturnValue(!shouldFlip(livingEntity));
    }

    @Unique
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        if (rightFlip(entity)) {
            matrices.translate(1.0F, (entity.getWidth() - 0.2F) / scale, 0.0F);
            if (entity instanceof GhastEntity) matrices.scale(4.5F, 4.5F, 4.5F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        }
    }

    @Unique
    private static boolean shouldFlip(LivingEntity entity) {
        if (entity instanceof PlayerEntity || entity.hasCustomName()) {
            return rightFlip(entity);
        }
        return false;
    }

    @Unique
    private static boolean rightFlip(LivingEntity entity) {
        if ("Lunchbone".equals(Formatting.strip(entity.getName().getString()))) {
            return !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
        }
        return false;
    }

    @Override
    public Identifier getTexture(T entity) {
        return Identifier.of(ThePaleForest.MOD_ID, "textures/entity/empty.png");
    }
}

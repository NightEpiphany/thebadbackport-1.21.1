package com.moigferdsrte.pale_forest.mixin;

import com.google.common.collect.Lists;
import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityFlipMixinV1<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {

    @Shadow
    protected M model;

    @Shadow
    @Final
    protected final List<FeatureRenderer<T, M>> features = Lists.newArrayList();

    protected LivingEntityFlipMixinV1(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
        super(ctx);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (shouldFlip(livingEntity)) {
            matrixStack.push();
            this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, g);
            this.model.riding = livingEntity.hasVehicle();
            this.model.child = livingEntity.isBaby();
            float h = MathHelper.lerpAngleDegrees(g, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
            float j = MathHelper.lerpAngleDegrees(g, livingEntity.prevHeadYaw, livingEntity.headYaw);
            float k = j - h;
            if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof LivingEntity livingEntity2) {
                h = MathHelper.lerpAngleDegrees(g, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
                k = j - h;
                float l = MathHelper.wrapDegrees(k);
                if (l < -85.0F) {
                    l = -85.0F;
                }

                if (l >= 85.0F) {
                    l = 85.0F;
                }

                h = j - l;
                if (l * l > 2500.0F) {
                    h += l * 0.2F;
                }

                k = j - h;
            }

            float m = MathHelper.lerp(g, livingEntity.prevPitch, livingEntity.getPitch());

            //flip
            m *= -1.0F;
            k *= -1.0F;

            k = MathHelper.wrapDegrees(k);
            if (livingEntity.isInPose(EntityPose.SLEEPING)) {
                Direction direction = livingEntity.getSleepingDirection();
                if (direction != null) {
                    float n = livingEntity.getEyeHeight(EntityPose.STANDING) - 0.1F;
                    matrixStack.translate((float) (-direction.getOffsetX()) * n, 0.0F, (float) (-direction.getOffsetZ()) * n);
                }
            }

            float lx = livingEntity.getScale();
            matrixStack.scale(lx, lx, lx);
            float n = this.getAnimationProgress(livingEntity, g);
            this.setupTransforms(livingEntity, matrixStack, n, h, g, lx);
            matrixStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(livingEntity, matrixStack, g);
            matrixStack.translate(0.0F, -1.501F, 0.0F);
            float o = 0.0F;
            float p = 0.0F;
            if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
                o = livingEntity.limbAnimator.getSpeed(g);
                p = livingEntity.limbAnimator.getPos(g);
                if (livingEntity.isBaby()) {
                    p *= 3.0F;
                }

                if (o > 1.0F) {
                    o = 1.0F;
                }
            }

            this.model.animateModel(livingEntity, p, o, g);
            this.model.setAngles(livingEntity, p, o, n, k, m);
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            boolean bl = this.isVisible(livingEntity);
            boolean bl2 = !bl && !livingEntity.isInvisibleTo(minecraftClient.player);
            boolean bl3 = minecraftClient.hasOutline(livingEntity);
            RenderLayer renderLayer = this.getRenderLayer(livingEntity, bl, bl2, bl3);
            if (renderLayer != null) {
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
                int q = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
                this.model.render(matrixStack, vertexConsumer, i, q, bl2 ? 654311423 : -1);
            }

            if (!livingEntity.isSpectator()) {
                for (FeatureRenderer<T, M> featureRenderer : this.features) {
                    featureRenderer.render(matrixStack, vertexConsumerProvider, i, livingEntity, p, o, g, n, k, m);
                }
            }

            matrixStack.pop();
            ci.cancel();
        }
    }

    @Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void label(T livingEntity, CallbackInfoReturnable<Boolean> cir) {

        if (livingEntity instanceof PlayerEntity player && "NightEpiphany".equals(Formatting.strip(player.getName().getString()))) cir.setReturnValue(true);

        if (shouldFlip(livingEntity))
            cir.setReturnValue(!shouldFlip(livingEntity));
    }

    @Unique
    @Nullable
    protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = ((EntityRenderer) (Object) this).getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Unique
    private static int getOverlay(LivingEntity entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    @Unique
    protected boolean isVisible(T entity) {
        return !entity.isInvisible();
    }

    @Unique
    protected boolean isShaking(T entity) {
        return entity.isFrozen();
    }

    @Unique
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        if (this.isShaking(entity)) {
            bodyYaw += (float)(Math.cos((double)entity.age * 3.25) * Math.PI * 0.4F);
        }

        if (!entity.isInPose(EntityPose.SLEEPING)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - bodyYaw));
        }

        if (entity.deathTime > 0) {
            float f = ((float)entity.deathTime + tickDelta - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f * this.getLyingAngle(entity)));
        } else if (entity.isUsingRiptide()) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - entity.getPitch()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(((float)entity.age + tickDelta) * -75.0F));
        } else if (entity.isInPose(EntityPose.SLEEPING)) {
            Direction direction = entity.getSleepingDirection();
            float g = direction != null ? getYaw(direction) : bodyYaw;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.getLyingAngle(entity)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270.0F));
        }else if (shouldFlipUpsideDown(entity)) {
            matrices.translate(0.0F, (entity.getHeight() + 0.1F) / scale, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        }  else if (leftFlip(entity)) {
            matrices.translate(-1.0F, (entity.getWidth() - 0.2F) / scale, 0.0F);
            if (entity instanceof GhastEntity) matrices.scale(4.5F, 4.5F, 4.5F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));
        }
    }

    @Unique
    private static float getYaw(Direction direction) {
        return switch (direction) {
            case SOUTH -> 90.0F;
            case NORTH -> 270.0F;
            case EAST -> 180.0F;
            default -> 0.0F;
        };
    }

    @Unique
    protected float getLyingAngle(T entity) {
        return 90.0F;
    }

    @Unique
    protected float getAnimationCounter(T entity, float tickDelta) {
        return 0.0F;
    }

    @Unique
    protected void scale(T entity, MatrixStack matrices, float amount) {
    }

    @Unique
    protected float getAnimationProgress(T entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }

    @Unique
    protected float getHandSwingProgress(T entity, float tickDelta) {
        return entity.getHandSwingProgress(tickDelta);
    }

    @Unique
    private static boolean shouldFlip(LivingEntity entity) {
        if (entity instanceof PlayerEntity || entity.hasCustomName()) {
            return leftFlip(entity);
        }
        return false;
    }

    @Unique
    private static boolean shouldFlipUpsideDown(LivingEntity entity) {
        if (entity instanceof PlayerEntity || entity.hasCustomName()) {
            String string = Formatting.strip(entity.getName().getString());
            if ("Dinnerbone".equals(string) || "Grumm".equals(string)) {
                return !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
            }
        }

        return false;
    }



    @Unique
    private static boolean leftFlip(LivingEntity entity) {
        if ("Breakfastbone".equals(Formatting.strip(entity.getName().getString()))) {
            return !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
        }
        return false;
    }

    @Override
    public Identifier getTexture(T entity) {
        return Identifier.of(ThePaleForest.MOD_ID, "textures/entity/empty.png");
    }
}

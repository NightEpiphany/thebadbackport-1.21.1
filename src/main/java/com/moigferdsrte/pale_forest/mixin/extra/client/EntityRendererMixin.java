package com.moigferdsrte.pale_forest.mixin.extra.client;

import com.moigferdsrte.pale_forest.api.leash.LeashRenderer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Unique
    private LeashRenderer<T> leashRenderer;

    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void vb$init(EntityRendererFactory.Context ctx, CallbackInfo ci) {
        this.leashRenderer = new LeashRenderer<>(this.dispatcher);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderAdditional(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        this.leashRenderer.render(entity, tickDelta, matrices, vertexConsumers);
    }


    @Inject(method = "shouldRender", at = @At("TAIL"), cancellable = true)
    private void shouldRenderLeash(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.leashRenderer.shouldRender(entity, frustum, cir.getReturnValue()));
    }
}

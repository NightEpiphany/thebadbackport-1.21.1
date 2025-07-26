package com.moigferdsrte.pale_forest.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class LabelRendererMixin<T extends Entity> {

    @Mutable
    @Final
    @Shadow
    protected final EntityRenderDispatcher dispatcher;


    @Shadow
    @Final
    private TextRenderer textRenderer;

    public LabelRendererMixin(EntityRenderDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"))
    private void render(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
        if ("Breakfastbone".equals(Formatting.strip(text.getString())) || "Lunchbone".equals(Formatting.strip(text.getString()))) {
            double d = this.dispatcher.getSquaredDistanceToCamera(entity);
            if (!(d > 4096.0)) {
                Vec3d vec3d = entity.getAttachments().getPointNullable(EntityAttachmentType.NAME_TAG, 0, entity.getYaw(tickDelta));
                assert vec3d != null;
                vec3d.rotateZ("Breakfastbone".equals(Formatting.strip(text.getString())) ? 90.0F : -90.0F);
                {
                    boolean bl = !entity.isSneaky();
                    matrices.push();
                    matrices.translate(vec3d.x, vec3d.y + 0.5, vec3d.z);
                    matrices.scale(0.025F, -0.025F, 0.025F);
                    Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                    float f = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
                    int j = (int)(f * 255.0F) << 24;
                    TextRenderer textRenderer = this.textRenderer;
                    float g = (float)(-textRenderer.getWidth(text) / 2);
                    textRenderer.draw(
                            text, g, (float)0, 553648127, false, matrix4f, vertexConsumers, bl ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, j, light
                    );
                    if (bl) {
                        textRenderer.draw(text, g, (float)0, Colors.WHITE, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
                    }

                    matrices.pop();
                }
            }
        }
    }
}

package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class HappyGhastGoggleFeatureRenderer extends FeatureRenderer<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> {

    private final ModelPart goggle;

    public HappyGhastGoggleFeatureRenderer(FeatureRendererContext<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> context, GoggleModel model) {
        super(context);
        this.goggle = model.goggle;
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light, HappyGhastEntity entity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch) {
        matrices.push();

        if (entity.hasHarness() && entity.isTamed() && !entity.hasPassengers()) {
            matrices.scale(0.51f, 0.51f, 0.51f);
            matrices.translate(0f, 0.656f, -0.1035f);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(45.0F));
            if (!entity.getFestive())
                this.goggle.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/harness/harness_goggle.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        -1
                );else {
                this.goggle.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/festive_harness/goggle/" + entity.getHarnessColor().getName() + "_harness_goggle.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        -1
                );
            }
        }

        if (entity.hasHarness() && entity.isTamed() && entity.hasPassengers()) {
            matrices.scale(0.51f, 0.51f, 0.51f);
            matrices.translate(0f, 1f, -0.55f);
            if (!entity.getFestive())
                this.goggle.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/harness/harness_goggle.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        -1
                );else {
                this.goggle.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/festive_harness/goggle/" + entity.getHarnessColor().getName() + "_harness_goggle.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        -1
                );
            }
        }

        matrices.pop();

    }
}

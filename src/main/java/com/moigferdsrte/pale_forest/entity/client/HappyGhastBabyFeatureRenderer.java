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

public class HappyGhastBabyFeatureRenderer extends FeatureRenderer<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> {

    private final ModelPart overlay;

    public HappyGhastBabyFeatureRenderer(FeatureRendererContext<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> context, ModelPart overlay) {
        super(context);
        this.overlay = overlay;
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
        if (entity.isBaby()) {
            matrices.scale(0.95f, 0.95f, 0.95f);
            matrices.translate(0f, 0.065f, 0f);
            this.overlay.render(
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast_baby_inner.png"))),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    -1);
        }
        matrices.pop();
    }
}

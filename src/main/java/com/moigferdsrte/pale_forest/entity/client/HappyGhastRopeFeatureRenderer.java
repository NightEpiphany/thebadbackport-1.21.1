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

public class HappyGhastRopeFeatureRenderer extends FeatureRenderer<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> {

    private final ModelPart rope;

    public HappyGhastRopeFeatureRenderer(FeatureRendererContext<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> context, ModelPart rope) {
        super(context);
        this.rope = rope;
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            HappyGhastEntity entity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch) {
        matrices.push();
        matrices.scale(1.0325f, 1.0325f, 1.0325f);
        matrices.translate(0, -0.14f, 0);
        if (entity.hasHarness() && entity.getLeashData() != null) {
            this.rope.render(
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast_ropes.png"))),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    -1);
        }
        matrices.pop();
    }
}

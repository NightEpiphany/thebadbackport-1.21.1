package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HappyGhastHarnessFeatureRenderer extends FeatureRenderer<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> {

    private final ModelPart harness;

    public HappyGhastHarnessFeatureRenderer(FeatureRendererContext<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> context, ModelPart harness) {
        super(context);
        this.harness = harness;
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
        matrices.scale(1.0675f, 1.0675f, 1.0675f);
        matrices.translate(0, -0.14f, 0);
        if (entity.hasHarness()) {
            this.harness.render(
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Identifier.of(ThePaleForest.MOD_ID,
                            entity.getFestive() ? "textures/entity/ghast/festive_harness/" + entity.getHarnessColor().getName() + "_harness.png" :
                            "textures/entity/ghast/harness/harness_" + entity.getHarnessColor().getName() + ".png"
                    ))),
                    light,
                    OverlayTexture.DEFAULT_UV,
                    -1);
        }
        matrices.pop();
    }
}

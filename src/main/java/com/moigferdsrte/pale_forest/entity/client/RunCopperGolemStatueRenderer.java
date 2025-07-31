package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.custom.CopperGolemStatueBlock;
import com.moigferdsrte.pale_forest.entity.block.RunCopperGolemStatueBlockEntity;
import com.moigferdsrte.pale_forest.entity.block.SitCopperGolemStatueBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RunCopperGolemStatueRenderer implements BlockEntityRenderer<RunCopperGolemStatueBlockEntity> {

    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(ThePaleForest.MOD_ID, "copper_golem_running"), "main");

    private final ModelPart golem;

    @Environment(EnvType.CLIENT)
    public RunCopperGolemStatueRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart modelPart = context.getLayerModelPart(LAYER);
        this.golem = modelPart.getChild("golem");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData golem = modelPartData.addChild("golem", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        golem.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -9.0F, 8.0F, 5.0F, 10.0F, new Dilation(0.0F))
                .uv(37, 8).cuboid(-4.0F, -10.0F, -5.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 0).cuboid(-5.0F, -14.0F, -6.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(56, 0).cuboid(-4.0F, -3.0F, -10.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -10.0F, 4.0F));

        ModelPartData arms = golem.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -9.0F, 2.0F));

        arms.addChild("right_r1", ModelPartBuilder.create().uv(50, 16).cuboid(-1.0F, -2.0F, -3.0F, 3.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -1.0F, -1.0908F, 0.0F, 0.0F));

        arms.addChild("left_r1", ModelPartBuilder.create().uv(36, 16).cuboid(-2.0F, -2.0F, -3.0F, 3.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 0.0F, -1.0F, 1.0472F, 0.0F, 0.0F));

        golem.addChild("body", ModelPartBuilder.create().uv(0, 15).cuboid(-7.0F, 1.0F, -7.0F, 8.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -12.0F, 4.0F));

        ModelPartData legs = golem.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        legs.addChild("left_r2", ModelPartBuilder.create().uv(16, 27).cuboid(-3.1F, -2.0F, -3.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -4.0F, 1.0F, 0.5672F, 0.0F, 0.0F));

        legs.addChild("right_r2", ModelPartBuilder.create().uv(0, 27).cuboid(-2.9F, -2.0F, -3.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -3.0F, -1.0F, -0.6109F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(
            RunCopperGolemStatueBlockEntity entity,
            float tickDelta,
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay) {
        BlockState cachedState = entity.getCachedState();
        if (cachedState.getBlock() instanceof CopperGolemStatueBlock block) {
            String prefix;
            switch (block.getDegradationLevel()) {
                case EXPOSED -> prefix = "exposed_";
                case WEATHERED -> prefix = "weathered_";
                case OXIDIZED -> prefix = "oxidized_";
                default -> prefix = "";
            }
            RenderLayer renderLayer = RenderLayer.getEntitySolid(Identifier.of(ThePaleForest.MOD_ID, "textures/entity/copper_golem/" + prefix + "copper_golem.png"));

            matrixStack.push();
            matrixStack.scale(0.909301251F, 0.909301251F, 0.909301251F);
            matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180.0F));
            matrixStack.translate(0.5535F, -1.5F, -0.5F);
            switch (cachedState.get(CopperGolemStatueBlock.FACING)) {
                case WEST -> {
                    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                    matrixStack.translate(0.0402557F, 0.0F, 0.0F);
                }
                case EAST -> {
                    matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90.0F));
                    matrixStack.translate(-0.0402557F, 0.0F, 0.0F);
                }
                case NORTH -> matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            }
            this.golem.render(matrixStack, vertexConsumers.getBuffer(renderLayer), light, overlay);
            matrixStack.pop();
        }
    }
}

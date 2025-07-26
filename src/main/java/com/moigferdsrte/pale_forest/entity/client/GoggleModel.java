package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GoggleModel extends EntityModel<HappyGhastEntity> {

    public final ModelPart goggle;

    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(ThePaleForest.MOD_ID, "goggle_layer"), "main");


    public GoggleModel(ModelPart root) {
        this.goggle = root.getChild("goggle");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData goggle = modelPartData.addChild("goggle", ModelPartBuilder.create().uv(68, 39).cuboid(16.0F, -14.0F, -8.0F, 0.0F, 10.0F, 6.0F, new Dilation(0.0F))
                .uv(51, 19).cuboid(-16.0F, -14.0F, -8.0F, 32.0F, 10.0F, 0.0F, new Dilation(0.0F))
                .uv(54, 39).mirrored().cuboid(-16.0F, -14.0F, -8.0F, 0.0F, 10.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(HappyGhastEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.goggle.render(matrices, vertices, light, overlay, color);
    }
}

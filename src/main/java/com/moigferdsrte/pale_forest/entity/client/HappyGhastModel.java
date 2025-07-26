package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class HappyGhastModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart[] tentacles = new ModelPart[9];

    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(ThePaleForest.MOD_ID, "happy_ghast"), "main");

    public HappyGhastModel(ModelPart root) {
        this.root = root;

        for (int i = 0; i < this.tentacles.length; i++) {
            this.tentacles[i] = root.getChild(getTentacleName(i));
        }
    }

    private static String getTentacleName(int index) {
        return "tentacle" + index;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 17.6F, 0.0F)
        );
        Random random = Random.create(1660L);

        for (int i = 0; i < 9; i++) {
            float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
            float g = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
            int j = random.nextInt(4) + 4;
            modelPartData.addChild(
                    getTentacleName(i), ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, (float)j, 2.0F), ModelTransform.pivot(f, 24.6F, g)
            );
        }

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        for (int i = 0; i < this.tentacles.length; i++) {
            this.tentacles[i].pitch = 0.2F * MathHelper.sin(animationProgress * 0.32F + (float)i) + 0.4F;
        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}

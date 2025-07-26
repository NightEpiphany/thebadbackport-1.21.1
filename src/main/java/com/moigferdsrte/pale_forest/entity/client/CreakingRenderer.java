package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreakingRenderer extends MobEntityRenderer<CreakingEntity, CreakingModelV2<CreakingEntity>> {

    public static final Identifier TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/creaking/creaking_v2.png");

    public CreakingRenderer(EntityRendererFactory.Context context) {
        super(context, new CreakingModelV2<>(context.getPart(ModModelLayers.CREAKING)), 0.8f);
    }

    @Override
    public Identifier getTexture(CreakingEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(CreakingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }else matrixStack.scale(1.0f, 1.0f, 1.0f);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

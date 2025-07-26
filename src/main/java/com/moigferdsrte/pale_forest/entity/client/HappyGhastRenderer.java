package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HappyGhastRenderer extends MobEntityRenderer<HappyGhastEntity, HappyGhastModel<HappyGhastEntity>> {

    private static final Identifier NO_HARNESS_BABY_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast_baby.png");
    private static final Identifier NO_HARNESS_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast.png");
    private static final Identifier NO_HARNESS_TAMED_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast_tamed.png");
    private static final Identifier ANGRY_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/ghast/happy_ghast_angry.png");

    public HappyGhastRenderer(EntityRendererFactory.Context context) {
        super(context, new HappyGhastModel<>(context.getPart(HappyGhastModel.LAYER)), 0.5F);
        this.addFeature(new HappyGhastHarnessFeatureRenderer(this, context.getPart(HappyGhastModel.LAYER)));
        this.addFeature(new HappyGhastRopeFeatureRenderer(this, context.getPart(HappyGhastModel.LAYER)));
        this.addFeature(new HappyGhastBabyFeatureRenderer(this, context.getPart(HappyGhastModel.LAYER)));
        this.addFeature(new HappyGhastGoggleFeatureRenderer(this, new GoggleModel(context.getPart(GoggleModel.LAYER))));
    }

    @Override
    protected void scale(HappyGhastEntity ghastEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.75F, 1.75F, 1.75F);
    }

    @Override
    public void render(HappyGhastEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.48F, 0.48F, 0.48F);
        } else {
            matrixStack.scale(1.75F, 1.75F, 1.75F);
        }
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(HappyGhastEntity entity) {
        return entity.isBaby() ? NO_HARNESS_BABY_TEXTURE : entity.isTamed() ? NO_HARNESS_TAMED_TEXTURE : entity.isAngry() ? ANGRY_TEXTURE : NO_HARNESS_TEXTURE;
    }
}

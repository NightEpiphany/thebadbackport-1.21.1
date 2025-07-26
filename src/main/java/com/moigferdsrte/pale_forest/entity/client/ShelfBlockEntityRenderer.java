package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.block.custom.ShelfBlock;
import com.moigferdsrte.pale_forest.entity.block.ShelfBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class ShelfBlockEntityRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ShelfBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        DefaultedList<ItemStack> defaultedList = entity.getItemsStored();
        Direction direction = entity.getCachedState().get(ShelfBlock.FACING);

        for (int l = 0; l < defaultedList.size(); l++) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack != ItemStack.EMPTY) {
                matrixStack.push();
                matrixStack.translate(-0.11033542F, 0.82921875F, 0.21255F);
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));


                matrixStack.translate(-0.3125F, -0.3125F, 0.0F);

                float g = -direction.asRotation();
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g));
                switch (direction) {
                    case SOUTH -> matrixStack.translate(-0.3121155F * l, 0.000001F * l, 0.001450001F);
                    case NORTH -> {
                        matrixStack.translate(0.60153535F, 0.0F * l, 0.0F);
                        matrixStack.translate(-0.3121155F * l, 0.000001F * l, 0.581450001F);
                    }
                    case WEST -> {
                        matrixStack.translate(0.02F, 0.0F * l, 0.585465706F);
                        matrixStack.translate(-0.311450001F * l , 0.000001F * l, 0.0F);
                    }
                    case EAST -> {
                        matrixStack.translate(0.60153535F, 0.0F * l, 0.0F);
                        matrixStack.translate(-0.316145001F * l , 0.000001F * l, 0.0F);
                    }
                }
                matrixStack.scale(0.3301251F, 0.3301251F, 0.3301251F);
                this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, entity.getWorld(), 0);
                matrixStack.pop();
            }
        }
    }
}

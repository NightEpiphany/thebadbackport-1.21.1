package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.entity.animation.CreakingAnimation;
import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CreakingModel<T extends CreakingEntity> extends SinglePartEntityModel<T> {
	private final ModelPart creaking;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	public CreakingModel(ModelPart root) {
		this.creaking = root.getChild("creaking");
		this.head = creaking.getChild("head");
		this.body = this.creaking.getChild("body");
		this.left_arm = this.creaking.getChild("left_arm");
		this.right_arm = this.creaking.getChild("right_arm");
		this.left_leg = this.creaking.getChild("left_leg");
		this.right_leg = this.creaking.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData creaking = modelPartData.addChild("creaking", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = creaking.addChild("head", ModelPartBuilder.create().uv(0, 37).cuboid(-12.0F, -16.0F, 0.0F, 6.0F, 16.0F, 0.0F, new Dilation(0.0F))
		.uv(22, 27).cuboid(-6.0F, -14.0F, -2.5F, 6.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 22).cuboid(-6.0F, -10.0F, -2.5F, 6.0F, 10.0F, 5.0F, new Dilation(0.0F))
		.uv(35, 36).cuboid(0.0F, -16.0F, 0.0F, 6.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = creaking.addChild("body", ModelPartBuilder.create().uv(17, 36).cuboid(2.0F, 5.0F, -1.5F, 4.0F, 7.0F, 5.0F, new Dilation(0.0F))
		.uv(23, 0).cuboid(-3.0F, 5.0F, -1.0F, 7.0F, 7.0F, 4.0F, new Dilation(0.0F))
		.uv(18, 17).cuboid(-6.0F, 0.0F, -1.5F, 12.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_arm = creaking.addChild("left_arm", ModelPartBuilder.create().uv(24, 49).cuboid(1.0F, 10.0F, -1.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(23, 11).cuboid(1.0F, -5.0F, -1.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(12, 48).cuboid(1.0F, -2.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData right_arm = creaking.addChild("right_arm", ModelPartBuilder.create().uv(47, 15).cuboid(-4.0F, 10.0F, -1.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(47, 42).cuboid(-4.0F, -2.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData left_leg = creaking.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.9F, 12.0F, -5.0F, 6.0F, 0.0F, 11.0F, new Dilation(0.0F))
		.uv(45, 0).cuboid(-0.9F, 0.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

		ModelPartData right_leg = creaking.addChild("right_leg", ModelPartBuilder.create().uv(0, 11).cuboid(-4.1F, 12.0F, -5.0F, 6.0F, 0.0F, 11.0F, new Dilation(0.0F))
		.uv(47, 27).cuboid(-1.1F, 0.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.creaking;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(headYaw, headPitch);
		this.animateMovement(CreakingAnimation.WALK, limbAngle, limbDistance, 2f, 2.5f);
		this.updateAnimation(CreakingEntity.idleAnimation, CreakingAnimation.IDLE, animationProgress, 1f);
		this.updateAnimation(CreakingEntity.attackAnimation,CreakingAnimation.ATTACK, animationProgress, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
		this.head.yaw = headYaw * 0.017453292f;
		this.head.pitch = headPitch * 0.017453292f;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		creaking.render(matrices, vertices, light, overlay, color);
	}
}
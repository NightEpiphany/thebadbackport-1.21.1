package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.entity.animation.CreakingAnimationV2;
import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

import java.util.List;

@Environment(EnvType.CLIENT)
public class CreakingModelV2<T extends CreakingEntity> extends SinglePartEntityModel<T> {
    public static final List<ModelPart> NO_PARTS = List.of();
    private final ModelPart root;
    private final ModelPart head;
    private final List<ModelPart> headParts;

    private boolean eyesGlowing;

    public CreakingModelV2(ModelPart root) {
        this.root = root.getChild("root");
        ModelPart upperBody = this.root.getChild("upper_body");
        this.head = upperBody.getChild("head");
        this.headParts = List.of(this.head);
    }

    private static ModelData createMesh() {
        ModelData mesh = new ModelData();
        ModelPartData base = mesh.getRoot();
        ModelPartData root = base.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData upperBody = root.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, -19.0F, 0.0F));
        upperBody.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-3.0F, -10.0F, -3.0F, 6.0F, 10.0F, 6.0F)
                        .uv(28, 31)
                        .cuboid(-3.0F, -13.0F, -3.0F, 6.0F, 3.0F, 6.0F)
                        .uv(12, 40)
                        .cuboid(3.0F, -13.0F, 0.0F, 9.0F, 14.0F, 0.0F)
                        .uv(34, 12)
                        .cuboid(-12.0F, -14.0F, 0.0F, 9.0F, 14.0F, 0.0F),
                ModelTransform.pivot(-3.0F, -11.0F, 0.0F)
        );
        upperBody.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 16)
                        .cuboid(0.0F, -3.0F, -3.0F, 6.0F, 13.0F, 5.0F)
                        .uv(24, 0)
                        .cuboid(-6.0F, -4.0F, -3.0F, 6.0F, 7.0F, 5.0F),
                ModelTransform.pivot(0.0F, -7.0F, 1.0F)
        );
        upperBody.addChild(
                "right_arm",
                ModelPartBuilder.create()
                        .uv(22, 13)
                        .cuboid(-2.0F, -1.5F, -1.5F, 3.0F, 21.0F, 3.0F)
                        .uv(46, 0)
                        .cuboid(-2.0F, 19.5F, -1.5F, 3.0F, 4.0F, 3.0F),
                ModelTransform.pivot(-7.0F, -9.5F, 1.5F)
        );
        upperBody.addChild(
                "left_arm",
                ModelPartBuilder.create()
                        .uv(30, 40)
                        .cuboid(0.0F, -1.0F, -1.5F, 3.0F, 16.0F, 3.0F)
                        .uv(52, 12)
                        .cuboid(0.0F, -5.0F, -1.5F, 3.0F, 4.0F, 3.0F)
                        .uv(52, 19)
                        .cuboid(0.0F, 15.0F, -1.5F, 3.0F, 4.0F, 3.0F),
                ModelTransform.pivot(6.0F, -9.0F, 0.5F)
        );
        root.addChild(
                "left_leg",
                ModelPartBuilder.create()
                        .uv(42, 40)
                        .cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 16.0F, 3.0F)
                        .uv(45, 55)
                        .cuboid(-1.5F, 15.7F, -4.5F, 5.0F, 0.0F, 9.0F),
                ModelTransform.pivot(1.5F, -16.0F, 0.5F)
        );
        root.addChild(
                "right_leg",
                ModelPartBuilder.create()
                        .uv(0, 34)
                        .cuboid(-3.0F, -1.5F, -1.5F, 3.0F, 19.0F, 3.0F)
                        .uv(45, 46)
                        .cuboid(-5.0F, 17.2F, -4.5F, 5.0F, 0.0F, 9.0F)
                        .uv(12, 34)
                        .cuboid(-3.0F, -4.5F, -1.5F, 3.0F, 3.0F, 3.0F),
                ModelTransform.pivot(-1.0F, -17.5F, 0.5F)
        );
        return mesh;
    }

    public static TexturedModelData createBodyLayer() {
        ModelData mesh = createMesh();
        return TexturedModelData.of(mesh, 64, 64);
    }

    public List<ModelPart> getHeadParts() {
        return !this.eyesGlowing ? NO_PARTS : this.headParts;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
        this.eyesGlowing = entity.isAttacking();
//        if (entity.isTearingDown()) {
//            this.eyesGlowing = entity.hasGlowingEyes();
//        } else {
//            this.eyesGlowing = entity.isActive();
//        }
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.head.pitch = headPitch * ((float) Math.PI / 180F);
        this.head.yaw = headYaw * ((float) Math.PI / 180F);

        this.animateMovement(CreakingAnimationV2.CREAKING_WALK, limbAngle, limbDistance, 1.0F, 1.0F);
        this.updateAnimation(entity.attackAnimationState, CreakingAnimationV2.CREAKING_ATTACK, animationProgress);
        this.updateAnimation(entity.invulnerabilityAnimationState, CreakingAnimationV2.CREAKING_INVULNERABLE, animationProgress);
        this.updateAnimation(entity.deathAnimationState, CreakingAnimationV2.CREAKING_DEATH, animationProgress);
    }
}

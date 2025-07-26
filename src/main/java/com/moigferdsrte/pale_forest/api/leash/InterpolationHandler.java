package com.moigferdsrte.pale_forest.api.leash;

import com.moigferdsrte.pale_forest.mixin.extra.access.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class InterpolationHandler {
    private final Entity entity;
    private final int interpolationSteps;
    private final InterpolationData data = new InterpolationData(0, Vec3d.ZERO, 0.0F, 0.0F);
    @Nullable private Vec3d previousTickPosition;
    @Nullable private Vec2f previousTickRot;

    public InterpolationHandler(Entity entity, int steps) {
        this.interpolationSteps = steps;
        this.entity = entity;
    }

    public void interpolateTo(Vec3d position, float y, float x) {
        if (this.interpolationSteps == 0) {
            this.entity.setPos(position.x, position.y, position.z);
            this.entity.setYaw(y);
            this.entity.setPitch(x);
            this.entity.resetPosition();
            ((EntityAccessor) this.entity).callRefreshPosition();
            this.cancel();
        } else {
            this.data.steps = this.interpolationSteps;
            this.data.position = position;
            this.data.yRot = y;
            this.data.xRot = x;
            this.previousTickPosition = this.entity.getPos();
            this.previousTickRot = new Vec2f(this.entity.getPitch(), this.entity.getYaw());
        }
    }

    public boolean hasActiveInterpolation() {
        return this.data.steps > 0;
    }

    public void interpolate() {
        if (!this.hasActiveInterpolation()) {
            this.cancel();
        } else {
            double progress = 1.0 / this.data.steps;
            if (this.previousTickPosition != null) {
                Vec3d movement = this.entity.getPos().subtract(this.previousTickPosition);
                if (this.entity.getWorld().isSpaceEmpty(this.entity, ((EntityAccessor) this.entity).getDimensions().getBoxAt(this.data.position.add(movement)))) {
                    this.data.addDelta(movement);
                }
            }

            if (this.previousTickRot != null) {
                float yRot = this.entity.getYaw() - this.previousTickRot.y;
                float xRot = this.entity.getPitch() - this.previousTickRot.x;
                this.data.addRotation(yRot, xRot);
            }

            double x = MathHelper.lerp(progress, this.entity.getX(), this.data.position.x);
            double y = MathHelper.lerp(progress, this.entity.getY(), this.data.position.y);
            double z = MathHelper.lerp(progress, this.entity.getZ(), this.data.position.z);
            Vec3d position = new Vec3d(x, y, z);

            float yRot = MathHelper.lerpAngleDegrees((float) progress, this.entity.getYaw(), this.data.yRot);
            float xRot = (float)MathHelper.lerp(progress, this.entity.getPitch(), this.data.xRot);

            this.entity.setPos(position.x, position.y, position.z);
            ((EntityAccessor) this.entity).callSetRotation(yRot, xRot);

            this.data.decrease();
            this.previousTickPosition = position;
            this.previousTickRot = new Vec2f(this.entity.getBlockX(), this.entity.getBlockY());
        }
    }

    public void cancel() {
        this.data.steps = 0;
        this.previousTickPosition = null;
        this.previousTickRot = null;
    }

    static class InterpolationData {
        protected int steps;
        Vec3d position;
        float yRot;
        float xRot;

        InterpolationData(int steps, Vec3d position, float yRot, float xRot) {
            this.steps = steps;
            this.position = position;
            this.yRot = yRot;
            this.xRot = xRot;
        }

        public void decrease() {
            this.steps--;
        }

        public void addDelta(Vec3d vec3) {
            this.position = this.position.add(vec3);
        }

        public void addRotation(float yRot, float xRot) {
            this.yRot += yRot;
            this.xRot += xRot;
        }
    }
}
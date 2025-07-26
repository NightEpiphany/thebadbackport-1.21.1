package com.moigferdsrte.pale_forest.mixin.extra;

import com.moigferdsrte.pale_forest.api.leash.InterpolationHandler;
import com.moigferdsrte.pale_forest.api.leash.LeashExtension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public abstract class BoatMixin extends Entity implements LeashExtension {
    @Unique
    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);

    public BoatMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "updateTrackedPositionAndAngles", at = @At("HEAD"), cancellable = true)
    private void vb$lerpTo(double x, double y, double z, float yRot, float xRot, int steps, CallbackInfo ci) {
        this.interpolation.interpolateTo(new Vec3d(x, y, z), yRot, xRot);
        ci.cancel();
    }

    @Inject(method = "updatePositionAndRotation", at = @At("HEAD"), cancellable = true)
    private void vb$tickLerp(CallbackInfo ci) {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.interpolation.cancel();
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }

        this.interpolation.interpolate();
        ci.cancel();
    }
}

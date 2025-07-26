package com.moigferdsrte.pale_forest.mixin.extra;

import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashExtentMixin {
    @Invoker static void callResolveLeashData(Entity entity, Leashable.LeashData leashData){}

    @Invoker
    static void callDetachLeash(Entity entity, boolean broadcast, boolean drop) {};

    @Inject(method = "tickLeash", at = @At("HEAD"), cancellable = true)
    private static <E extends Entity & Leashable> void vb$tickLeash(E entity, CallbackInfo ci) {
        if (entity instanceof HappyGhastEntity) {
            ci.cancel();
            Leashable.LeashData data = entity.getLeashData();
            if (data != null && data.unresolvedLeashData != null) {
                callResolveLeashData(entity, data);
            }

            if (data != null && data.leashHolder != null) {
                if (!entity.isAlive() || !data.leashHolder.isAlive()) {
                    callDetachLeash(entity, true, true);
                }

                Entity holder = entity.getLeashHolder();
                if (holder != null && holder.getWorld() == entity.getWorld()) {
                    float distanceFromHolder = entity.distanceTo(holder);
                    if (!entity.beforeLeashTick(holder, distanceFromHolder)) {
                        return;
                    }

                    if ((double) distanceFromHolder > (double) 16.0F) {
                        entity.detachLeash();
                    } else if ((double) distanceFromHolder > (double) 12.0F) {
                        entity.applyLeashElasticity(holder, distanceFromHolder);
                        entity.limitFallDistance();
                    } else {
                        entity.onShortLeashTick(holder);
                    }
                }
            }
        }
    }
}

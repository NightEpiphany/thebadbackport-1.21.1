package com.moigferdsrte.pale_forest.mixin.extra;

import com.moigferdsrte.pale_forest.api.leash.LeashDataExtension;
import com.moigferdsrte.pale_forest.api.leash.LeashExtension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashableMixin extends LeashExtension {
    @Shadow
    private static <E extends Entity & Leashable> void resolveLeashData(E entity, Leashable.LeashData leashData) { }

    @Inject(method = "tickLeash", at = @At("HEAD"), cancellable = true)
    private static <E extends Entity & Leashable> void onTickLeash(E entity, CallbackInfo ci) {
        Leashable.LeashData data = entity.getLeashData();
        if (data != null && data.unresolvedLeashData != null) {
            resolveLeashData(entity, data);
        }

        if (data != null && data.leashHolder != null) {
            if (!entity.isAlive() || !data.leashHolder.isAlive()) {
                entity.detachLeash(true, entity.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS));
            }

            Entity holder = entity.getLeashHolder();
            LeashExtension leashed = (LeashExtension) entity;
            if (holder != null && holder.getWorld() == entity.getWorld()) {
                double distance = leashed.leashDistanceTo(holder);
                leashed.whenLeashedTo(holder);
                if (distance > leashed.leashSnapDistance()) {
                    entity.getWorld().playSound(null, holder.getBlockPos(), SoundEvents.ENTITY_LEASH_KNOT_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    entity.detachLeash();
                } else if (distance > leashed.leashElasticDistance() - holder.getWidth() - entity.getWidth() && leashed.checkElasticInteractions(holder, data)) {
                    leashed.onElasticLeashPull();
                } else {
                    entity.onShortLeashTick(holder);
                }

                LeashDataExtension leashData = (LeashDataExtension) (Object) data;
                entity.setYaw((float) (entity.getYaw() - leashData.angularMomentum()));
                leashData.setAngularMomentum(leashData.angularMomentum() * LeashExtension.angularFriction(entity));
            }
        }

        ci.cancel();
    }

}

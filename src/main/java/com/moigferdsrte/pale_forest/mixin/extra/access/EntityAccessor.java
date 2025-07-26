package com.moigferdsrte.pale_forest.mixin.extra.access;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Invoker BlockPos callGetVelocityAffectingPos();

    @Accessor
    EntityDimensions getDimensions();

    @Invoker
    void callSetRotation(float yRot, float xRot);

    @Invoker void callRefreshPosition();
}

package com.moigferdsrte.pale_forest.mixin.extra.access;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface EntityRendererAccessor {

    @Invoker
    <T extends Entity> int callGetBlockLight(T entity, BlockPos pos);
}

package com.moigferdsrte.pale_forest.mixin.extra.client;

import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BoatEntityRenderer.class)
public class BoatRendererMixin extends EntityRendererMixin<BoatEntity> {
}

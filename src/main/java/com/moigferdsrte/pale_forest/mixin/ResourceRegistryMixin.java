package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.world.biome.DeadwoodColormapResourceSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class)
public class ResourceRegistryMixin {

    @Mutable
    @Final
    @Shadow
    private final ReloadableResourceManagerImpl resourceManager;

    public ResourceRegistryMixin(ReloadableResourceManagerImpl resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void load(RunArgs args, CallbackInfo ci) {
        this.resourceManager.registerReloader(new DeadwoodColormapResourceSupplier());
    }

}

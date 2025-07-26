package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.tags.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggTintMixin {

    @Final
    @Shadow
    private EntityType<?> type;
    @Mutable
    @Final
    @Shadow
    private final int primaryColor;
    @Mutable
    @Final
    @Shadow
    private final int secondaryColor;

    protected SpawnEggTintMixin(int primaryColor, int secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Inject(
            method = "getColor",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideColor(int tintIndex, CallbackInfoReturnable<Integer> cir) {
        SpawnEggItem item = (SpawnEggItem) (Object) this;
        if (Registries.ENTITY_TYPE.getId(this.type).getNamespace().equals("minecraft") && item.getDefaultStack().isIn(ModTags.SPAWN_EGG)
                || this.type == ModEntities.CREAKING || this.type == ModEntities.HAPPY_GHAST) {
            cir.setReturnValue(0xFFFFFF);
        }
        else cir.setReturnValue(tintIndex == 0 ? this.primaryColor : this.secondaryColor);
    }
}

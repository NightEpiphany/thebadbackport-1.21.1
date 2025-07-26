package com.moigferdsrte.pale_forest.mixin;


import com.moigferdsrte.pale_forest.block.custom.MoreBeehiveVariantBlock;
import com.moigferdsrte.pale_forest.block.custom.MoreCampfireVariantBlock;
import com.moigferdsrte.pale_forest.block.custom.ShelfBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof MoreBeehiveVariantBlock || state.getBlock() instanceof MoreCampfireVariantBlock) {
            cir.setReturnValue(true);
        }
        if (state.getBlock() instanceof ShelfBlock) {
            cir.setReturnValue(true);
        }
    }
}

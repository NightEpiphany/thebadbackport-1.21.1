package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.GoatEntityRenderer;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(GoatEntityRenderer.class)
public class GoatEntityRendererMixin {

    @Unique
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/goat/goat.png");

    @Unique
    private static final Identifier TEXTURE_SCREAMING = Identifier.of(ThePaleForest.MOD_ID, "textures/entity/goat/screaming_goat.png");


    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/GoatEntity;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"), cancellable = true)
    private void texture(GoatEntity goatEntity, CallbackInfoReturnable<Identifier> cir) {
        cir.setReturnValue(goatEntity.isScreaming() ? TEXTURE_SCREAMING : TEXTURE);
    }
}

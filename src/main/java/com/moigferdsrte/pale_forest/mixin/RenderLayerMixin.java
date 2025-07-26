package com.moigferdsrte.pale_forest.mixin;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderLayers.class)
public class RenderLayerMixin {
    @Shadow @Final private static Map<Block, RenderLayer> BLOCKS;

    @Inject(method = "<clinit>",at=@At("RETURN"))
    private static void onBlockInit(CallbackInfo ci) {
        BLOCKS.put(ModBlocks.FIREFLY_BUSH, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.CACTUS_FLOWER, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.SHORT_DRY_GRASS, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.TALL_DRY_GRASS, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.BUSH, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_CACTUS_FLOWER, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_SHORT_DRY_GRASS, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_BUSH, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_FIREFLY_BUSH, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_OAK_SAPLING, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_IVY, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_IVY_PLANT, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_GRASS, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_TALL_GRASS, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_TWIST_FERN, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_PALE_OAK_SAPLING, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_PALE_TWIST_FERN, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.PALE_OAK_LEAVES, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.OPEN_EYEBLOSSOM, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.CLOSED_EYEBLOSSOM, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_OPEN_EYEBLOSSOM, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.POTTED_CLOSED_EYEBLOSSOM, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.RESIN_CLUMP, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.LEAF_LITTER, RenderLayer.getCutout());
        BLOCKS.put(ModBlocks.WILDFLOWER, RenderLayer.getCutout());
    }
}

package com.moigferdsrte.pale_forest;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.entity.ModBlockEntities;
import com.moigferdsrte.pale_forest.entity.ModBoats;
import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.entity.client.*;
import com.moigferdsrte.pale_forest.item.ModItems;
import com.moigferdsrte.pale_forest.particles.FireflyParticleFactory;
import com.moigferdsrte.pale_forest.world.biome.DeadwoodColors;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GrassColors;

public class ThePaleForestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TerraformBoatClientHelper.registerModelLayers(ModBoats.PALE_OAK_BOAT, false);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALE_OAK_CAMPFIRE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALE_OAK_SOUL_CAMPFIRE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALE_MOSS_CARPET, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CREAKING, CreakingModelV2::createBodyLayer);

        EntityRendererRegistry.register(ModEntities.CREAKING, CreakingRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GoggleModel.LAYER, GoggleModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(HappyGhastModel.LAYER, HappyGhastModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.HAPPY_GHAST, HappyGhastRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ThePaleForest.FIREFLY_PARTICLE, FireflyParticleFactory.FireflyFactory::new);

        EntityRendererRegistry.register(ModEntities.BLUE_EGG, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.BROWN_EGG, FlyingItemEntityRenderer::new);

        BlockEntityRendererFactories.register(ModBlockEntities.SHELF, ShelfBlockEntityRenderer::new);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            assert view != null;
            if (view.getBiomeFabric(pos) != null) {
                Biome biome = view.getBiomeFabric(pos).value();
                assert pos != null;
                return biome.getGrassColorAt(pos.getX(), pos.getZ());
            }
            return 0x56d31c;
        }, ModBlocks.BUSH, ModBlocks.POTTED_BUSH, ModBlocks.WILDFLOWER);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x56d31c, ModItems.BUSH);

//        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
//            assert world != null;
//            if (world.getBiomeFabric(pos) == BiomeKeys.BADLANDS || world.getBiomeFabric(pos) == BiomeKeys.DESERT) {
//                return DeadwoodColors.getColor(world.getBiomeFabric(pos).value().getTemperature(), 0.95f, 1.0f);
//            }
//            return DeadwoodColors.DEFAULT;
//        }, ModBlocks.LEAF_LITTER);
    }
}

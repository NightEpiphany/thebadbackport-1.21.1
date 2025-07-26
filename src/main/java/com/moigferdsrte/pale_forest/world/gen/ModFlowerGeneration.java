package com.moigferdsrte.pale_forest.world.gen;

import com.moigferdsrte.pale_forest.world.ModPlacedFeatures;
import com.moigferdsrte.pale_forest.world.biome.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSources;
import net.minecraft.world.gen.GenerationStep;

public class ModFlowerGeneration {
    public static void generateFlowers() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.PALE_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PALE_TWIST_FERN_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.BADLANDS, BiomeKeys.SAVANNA_PLATEAU),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SHORT_DRY_GRASS_PLACED_KRY
        );

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.BADLANDS, BiomeKeys.SAVANNA_PLATEAU),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TALL_DRY_GRASS_PLACED_KRY
        );

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CACTUS_FLOWER_PLACED_KRY
        );

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.PALE_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PALE_GRASS_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.WINDSWEPT_FOREST,
                        BiomeKeys.WINDSWEPT_HILLS,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
                        BiomeKeys.RIVER,
                        BiomeKeys.FROZEN_RIVER,
                        BiomeKeys.FOREST,
                        BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST
                ),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSH_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeys.SWAMP,
                        BiomeKeys.MANGROVE_SWAMP
                ), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FIREFLY_BUSH_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.BIRCH_FOREST,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                BiomeKeys.MEADOW
        ), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WILDFLOWER_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.FOREST,
                BiomeKeys.DARK_FOREST,
                BiomeKeys.WOODED_BADLANDS
        ), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.LEAF_LITTER_PLACED_KEY);
    }
}

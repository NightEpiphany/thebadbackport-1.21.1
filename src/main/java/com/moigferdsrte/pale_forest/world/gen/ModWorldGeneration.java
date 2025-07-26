package com.moigferdsrte.pale_forest.world.gen;

import com.moigferdsrte.pale_forest.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {

    public static void registerWorldGenerations() {

        /*BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.PATCH_CACTUS_COMBINED
        );*/

        ModTreeGeneration.registerTrees();
        ModFlowerGeneration.generateFlowers();
    }
}

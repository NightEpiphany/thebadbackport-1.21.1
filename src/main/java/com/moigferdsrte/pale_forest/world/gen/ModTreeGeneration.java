package com.moigferdsrte.pale_forest.world.gen;

import com.moigferdsrte.pale_forest.world.ModPlacedFeatures;
import com.moigferdsrte.pale_forest.world.biome.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void registerTrees(){
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.PALE_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PALE_OAK_TREE_PLACED_KEY);
    }
}

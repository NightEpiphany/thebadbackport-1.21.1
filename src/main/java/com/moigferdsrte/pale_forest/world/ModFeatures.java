package com.moigferdsrte.pale_forest.world;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.world.feature.CactusWithFlowerFeature;
import com.moigferdsrte.pale_forest.world.feature.CactusWithFlowerFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;

public class ModFeatures {
    public static final Feature<CactusWithFlowerFeatureConfig> CACTUS_WITH_FLOWER = new CactusWithFlowerFeature(CactusWithFlowerFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, Identifier.of(ThePaleForest.MOD_ID, "cactus_with_flower"), CACTUS_WITH_FLOWER);
    }

    public static void init(){}
}

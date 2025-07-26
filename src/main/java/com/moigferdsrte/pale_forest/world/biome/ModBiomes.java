package com.moigferdsrte.pale_forest.world.biome;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.ModEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiomes {
    public static final RegistryKey<Biome> PALE_FOREST = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(ThePaleForest.MOD_ID, "pale_forest"));

    public static void globalOverWorldGeneration(GenerationSettings.LookupBackedBuilder builder){
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    private static Biome paleForestBiome(Registerable<Biome> context){
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.CREAKING, 2, 1, 2));

        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverWorldGeneration(generationSettings);
        DefaultBiomeFeatures.addMossyRocks(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addExtraGoldOre(generationSettings);

        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.TREES_PLAINS);

        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.5f)
                .temperature(0.7f)
                .generationSettings(generationSettings.build())
                .spawnSettings(spawnSettings.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xefe7e7)
                        .waterFogColor(0xf4e6e7)
                        .skyColor(0xd2c0ef)
                        .grassColor(0xa4a2a2)
                        .foliageColor(0xd9e5c3)
                        .fogColor(0xd6d4fa)
                        .build())
                .build();
    }

    public static void bootstrap(Registerable<Biome> context){
        context.register(PALE_FOREST, paleForestBiome(context));
    }
}

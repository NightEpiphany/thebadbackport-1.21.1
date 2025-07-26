package com.moigferdsrte.pale_forest.world;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PALE_OAK_TREE_PLACED_KEY = of("pale_oak_tree_placed");

    public static final RegistryKey<PlacedFeature> PALE_TWIST_FERN_PLACED_KEY = of("pale_twist_fern_placed");

    public static final RegistryKey<PlacedFeature> PALE_GRASS_PLACED_KEY = of("pale_grass_placed");

    public static final RegistryKey<PlacedFeature> BUSH_PLACED_KEY = of("bush_placed");

    public static final RegistryKey<PlacedFeature> FIREFLY_BUSH_PLACED_KEY = of("firefly_bush_placed");

    public static final RegistryKey<PlacedFeature> LEAF_LITTER_PLACED_KEY = of("leaf_litter_placed");

    public static final RegistryKey<PlacedFeature> WILDFLOWER_PLACED_KEY = of("wildflower_placed");

    public static final RegistryKey<PlacedFeature> SHORT_DRY_GRASS_PLACED_KRY = of("short_dry_grass_placed");

    public static final RegistryKey<PlacedFeature> TALL_DRY_GRASS_PLACED_KRY = of("tall_dry_grass_placed");

    public static final RegistryKey<PlacedFeature> CACTUS_FLOWER_PLACED_KRY = of("cactus_flower_placed");


    //Test
    public static final RegistryKey<PlacedFeature> PATCH_CACTUS_COMBINED = RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier.of(ThePaleForest.MOD_ID, "patch_cactus_with_flower")
    );

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {

        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);



        //Test
        /*PlacedFeatures.register(
                featureRegisterable,
                PATCH_CACTUS_COMBINED,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_CACTUS_WITH_FLOWER),
                List.of(
                        CountPlacementModifier.of(UniformIntProvider.create(0, 2)), // 生成数量
                        SquarePlacementModifier.of(),
                        PlacedFeatures.BOTTOM_TO_120_RANGE,
                        BiomePlacementModifier.of()
                )
        );*/

        register(featureRegisterable, SHORT_DRY_GRASS_PLACED_KRY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.SHORT_DRY_GRASS_KEY),
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, TALL_DRY_GRASS_PLACED_KRY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.TALL_DRY_GRASS_KEY),
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, CACTUS_FLOWER_PLACED_KRY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.CACTUS_FLOWER_KEY),
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


        register(featureRegisterable, PALE_OAK_TREE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.PALE_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(10, 0.1f, 2),
                        ModBlocks.PALE_OAK_SAPLING));

        register(featureRegisterable, PALE_TWIST_FERN_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.PALE_TWIST_FERN_KEY),
                RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, PALE_GRASS_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.PALE_GRASS_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, BUSH_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.BUSH_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, FIREFLY_BUSH_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FIREFLY_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, LEAF_LITTER_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.LEAF_LITTER_KEY),
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        register(featureRegisterable, WILDFLOWER_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.WILDFLOWER_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }


    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(ThePaleForest.MOD_ID, id));
    }
    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> modifiers
    ) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            PlacementModifier... modifiers
    ) {
        register(featureRegisterable, key, feature, List.of(modifiers));
    }
}

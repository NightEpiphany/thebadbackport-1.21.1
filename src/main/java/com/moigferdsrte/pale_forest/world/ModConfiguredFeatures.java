package com.moigferdsrte.pale_forest.world;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.BiasedToBottomIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_OAK_TREE_KEY = of("pale_oak_tree");

    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_TWIST_FERN_KEY = of("pale_twist_fern");

    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_GRASS_KEY = of("pale_grass");

    public static final RegistryKey<ConfiguredFeature<?, ?>> BUSH_KEY = of("bush");

    public static final RegistryKey<ConfiguredFeature<?, ?>> FIREFLY_KEY = of("firefly_bush");

    public static final RegistryKey<ConfiguredFeature<?, ?>> LEAF_LITTER_KEY = of("leaf_litter");

    public static final RegistryKey<ConfiguredFeature<?, ?>> WILDFLOWER_KEY = of("wildflower");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SHORT_DRY_GRASS_KEY = of("short_dry_grass");

    public static final RegistryKey<ConfiguredFeature<?, ?>> TALL_DRY_GRASS_KEY = of("tall_dry_grass");

    public static final RegistryKey<ConfiguredFeature<?, ?>> CACTUS_FLOWER_KEY = of("cactus_flower");


    //Test
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_WITH_FLOWER = RegistryKey.of(
            RegistryKeys.CONFIGURED_FEATURE,
            Identifier.of(ThePaleForest.MOD_ID, "patch_cactus_with_flower")
    );




    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {


        //Test
        /*ConfiguredFeatures.register(
                featureRegisterable,
                PATCH_CACTUS_WITH_FLOWER,
                Feature.BLOCK_COLUMN,
                new BlockColumnFeatureConfig(
                        List.of(
                                BlockColumnFeatureConfig.createLayer(
                                        BiasedToBottomIntProvider.create(2, 4),
                                        BlockStateProvider.of(Blocks.CACTUS)
                                )
                        ),
                        Direction.UP,
                        BlockPredicate.IS_AIR,
                        true
                )
        ); */



        register(featureRegisterable, SHORT_DRY_GRASS_KEY, Feature.RANDOM_PATCH,
                createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.SHORT_DRY_GRASS), 5));

        register(featureRegisterable, TALL_DRY_GRASS_KEY, Feature.RANDOM_PATCH,
                createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.TALL_DRY_GRASS), 4));

        register(featureRegisterable, CACTUS_FLOWER_KEY, Feature.RANDOM_PATCH,
                createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.CACTUS_FLOWER), 1));

        register(featureRegisterable, PALE_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.PALE_OAK_LOG),
                new DarkOakTrunkPlacer(6, 2, 1),
                BlockStateProvider.of(ModBlocks.PALE_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1)),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());

        register(featureRegisterable, PALE_TWIST_FERN_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(32, 3, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.PALE_TWIST_FERN)))));

        register(featureRegisterable, PALE_GRASS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 3, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.PALE_GRASS)))));

        register(featureRegisterable, BUSH_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 3, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BUSH)))));

        register(featureRegisterable, FIREFLY_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 3, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.FIREFLY_BUSH)))));

        register(featureRegisterable, LEAF_LITTER_KEY, Feature.RANDOM_PATCH,
        createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.LEAF_LITTER), 4));

        register(featureRegisterable, WILDFLOWER_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(32, 3, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILDFLOWER)))));
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(ThePaleForest.MOD_ID, id));
    }
    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ) {
        registerable.register(key, new ConfiguredFeature<FC, F>(feature, config));
    }
}

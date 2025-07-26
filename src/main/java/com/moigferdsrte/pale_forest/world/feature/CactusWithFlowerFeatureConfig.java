package com.moigferdsrte.pale_forest.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.BlockColumnFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public record CactusWithFlowerFeatureConfig(
        List<BlockColumnFeatureConfig.Layer> layers,
        Direction direction,
        BlockPredicate allowedPlacement,
        boolean prioritizeTip,
        float flowerChance,
        int height,
        BlockStateProvider flowerProvider
) implements FeatureConfig {
    public static final Codec<CactusWithFlowerFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockColumnFeatureConfig.Layer.CODEC.listOf().fieldOf("layers").forGetter(CactusWithFlowerFeatureConfig::layers),
                    Direction.CODEC.fieldOf("direction").forGetter(CactusWithFlowerFeatureConfig::direction),
                    BlockPredicate.BASE_CODEC.fieldOf("allowed_placement").forGetter(CactusWithFlowerFeatureConfig::allowedPlacement),
                    Codec.BOOL.fieldOf("prioritize_tip").forGetter(CactusWithFlowerFeatureConfig::prioritizeTip),
                    Codec.FLOAT.fieldOf("flower_chance").forGetter(CactusWithFlowerFeatureConfig::flowerChance),
                    Codec.INT.fieldOf("height").forGetter(CactusWithFlowerFeatureConfig::height),
                    BlockStateProvider.TYPE_CODEC.fieldOf("flower_provider").forGetter(CactusWithFlowerFeatureConfig::flowerProvider)
            ).apply(instance, CactusWithFlowerFeatureConfig::new)
    );
}

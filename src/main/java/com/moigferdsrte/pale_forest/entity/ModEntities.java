package com.moigferdsrte.pale_forest.entity;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.entity.custom.BlueEggEntity;
import com.moigferdsrte.pale_forest.entity.custom.BrownEggEntity;
import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModEntities {

    public static final EntityType<CreakingEntity> CREAKING = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, "creaking"),
            EntityType.Builder.create(CreakingEntity::new, SpawnGroup.CREATURE).dimensions(1f, 3f).build()
    );

    public static final EntityType<BlueEggEntity> BLUE_EGG = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, "blue_egg"),
            EntityType.Builder.<BlueEggEntity>create(BlueEggEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build()
    );

    public static final EntityType<BrownEggEntity> BROWN_EGG = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, "brown_egg"),
            EntityType.Builder.<BrownEggEntity>create(BrownEggEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build()
    );

    public static final EntityType<HappyGhastEntity> HAPPY_GHAST = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, "happy_ghast"),
            EntityType.Builder.create(HappyGhastEntity::new, SpawnGroup.CREATURE).dimensions(2.45f, 3.0754f).build()
    );

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, id), builder.build(type));
    }
}

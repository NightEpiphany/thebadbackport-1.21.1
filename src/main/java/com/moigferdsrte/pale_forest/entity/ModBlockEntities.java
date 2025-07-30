package com.moigferdsrte.pale_forest.entity;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.entity.block.CopperGolemStatueBlockEntity;
import com.moigferdsrte.pale_forest.entity.block.ShelfBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModBlockEntities {

    public static final BlockEntityType<ShelfBlockEntity> SHELF = create("shelf",
            BlockEntityType.Builder.create(ShelfBlockEntity::new,
                    ModBlocks.ACACIA_SHELF,
                    ModBlocks.BIRCH_SHELF,
                    ModBlocks.MANGROVE_SHELF,
                    ModBlocks.PALE_OAK_SHELF,
                    ModBlocks.OAK_SHELF,
                    ModBlocks.DARK_OAK_SHELF,
                    ModBlocks.WARPED_SHELF,
                    ModBlocks.CRIMSON_SHELF,
                    ModBlocks.JUNGLE_SHELF,
                    ModBlocks.BAMBOO_SHELF,
                    ModBlocks.CHERRY_SHELF,
                    ModBlocks.SPRUCE_SHELF
            ));

    public static final BlockEntityType<CopperGolemStatueBlockEntity> COPPER_GOLEM_STATUE = create("copper_golem_statue",
            BlockEntityType.Builder.create(CopperGolemStatueBlockEntity::new,
                    ModBlocks.COPPER_GOLEM_STATUE,
                    ModBlocks.EXPOSED_COPPER_GOLEM_STATUE,
                    ModBlocks.WEATHERED_COPPER_GOLEM_STATUE,
                    ModBlocks.OXIDIZED_COPPER_GOLEM_STATUE,
                    ModBlocks.WAXED_COPPER_GOLEM_STATUE,
                    ModBlocks.WAXED_EXPOSED_COPPER_GOLEM_STATUE,
                    ModBlocks.WAXED_OXIDIZED_COPPER_GOLEM_STATUE,
                    ModBlocks.WAXED_WEATHERED_COPPER_GOLEM_STATUE
                    )
            );

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ThePaleForest.MOD_ID, id), builder.build(type));
    }

    public static void init() {}
}

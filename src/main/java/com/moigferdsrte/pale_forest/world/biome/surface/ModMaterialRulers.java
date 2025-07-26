package com.moigferdsrte.pale_forest.world.biome.surface;


import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.world.biome.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRulers {

    private static final MaterialRules.MaterialRule DIRT = makeRules(Blocks.DIRT);

    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeRules(Blocks.GRASS_BLOCK);

    private static final MaterialRules.MaterialRule PALE_MOSS = makeRules(ModBlocks.PALE_MOSS_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1,0);
        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel,GRASS_BLOCK),DIRT);

        return MaterialRules.sequence(
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.PALE_FOREST),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, GRASS_BLOCK)),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PALE_MOSS)),

                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeRules(Block block){
        return MaterialRules.block(block.getDefaultState());
    }
}

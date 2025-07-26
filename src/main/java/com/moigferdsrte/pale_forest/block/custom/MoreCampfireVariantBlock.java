package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;

public class MoreCampfireVariantBlock extends CampfireBlock {

    private final String campfireWoodType;

    public MoreCampfireVariantBlock(MapColor mapColor, String campfireWoodType) {
        super(true, 1, AbstractBlock.Settings.copy(Blocks.CAMPFIRE));
        this.campfireWoodType = campfireWoodType;
    }

    public Item getPlanksItem() {
        return ModBlocks.PALE_OAK_PLANKS.asItem();
    }
}

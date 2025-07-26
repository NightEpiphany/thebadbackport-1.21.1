package com.moigferdsrte.pale_forest.block.custom;


import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class MoreBeehiveVariantBlock extends BeehiveBlock {
    public final String beehiveWoodType;

    public MoreBeehiveVariantBlock(MapColor colour, String beehiveWoodType) {
        super(AbstractBlock.Settings.copy(Blocks.BEEHIVE).mapColor(colour));
        this.beehiveWoodType = beehiveWoodType;
    }

    public MoreBeehiveVariantBlock(MapColor colour, BlockSoundGroup sound, String beehiveWoodType) {
        super(AbstractBlock.Settings.copy(Blocks.BEEHIVE).mapColor(colour).sounds(sound));
        this.beehiveWoodType = beehiveWoodType;
    }

    public Item getPlanksItem() {
        return ModBlocks.PALE_OAK_PLANKS.asItem();
    }
}

package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class PaleIvyPlantBlock extends AbstractPlantBlock {
    public static final MapCodec<PaleIvyPlantBlock> CODEC = createCodec(PaleIvyPlantBlock::new);
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    @Override
    public MapCodec<PaleIvyPlantBlock> getCodec() {
        return CODEC;
    }

    public PaleIvyPlantBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.PALE_IVY;
    }
}

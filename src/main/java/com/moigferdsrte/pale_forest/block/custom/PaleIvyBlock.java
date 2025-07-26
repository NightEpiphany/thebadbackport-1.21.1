package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

public class PaleIvyBlock extends AbstractPlantStemBlock {
    public static final MapCodec<PaleIvyBlock> CODEC = createCodec(PaleIvyBlock::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 10.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    public MapCodec<PaleIvyBlock> getCodec() {
        return CODEC;
    }

    public PaleIvyBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.PALE_IVY_PLANT;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}

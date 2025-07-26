package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TallDryGrassBlock extends ShortPlantBlock implements Fertilizable {

    public static final MapCodec<TallDryGrassBlock> CODEC = createCodec(TallDryGrassBlock::new);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public TallDryGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++)
                if (
                    random.nextBoolean() && world.getBlockState(pos.add(i, -1, j)).isIn(BlockTags.DIRT) && world.getBlockState(pos.add(i, 0, j)).isIn(BlockTags.AIR) ||
                        random.nextBoolean() && world.getBlockState(pos.add(i, -1, j)).isIn(BlockTags.SAND) && world.getBlockState(pos.add(i, 0, j)).isIn(BlockTags.AIR)
                ) {
                    world.setBlockState(pos.add(i, 0, j), ModBlocks.SHORT_DRY_GRASS.getDefaultState(), Block.NOTIFY_LISTENERS);
                }
    }
}

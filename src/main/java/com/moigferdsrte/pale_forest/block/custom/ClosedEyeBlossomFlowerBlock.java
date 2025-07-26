package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ClosedEyeBlossomFlowerBlock extends FlowerBlock {
    public ClosedEyeBlossomFlowerBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.impact(world, pos);
        super.randomDisplayTick(state, world, pos, random);
    }

    private void impact(World world, BlockPos pos){
        List<BlockPos> target = new ArrayList<>();

        for (int i = pos.getY() - 2; i <= pos.getY() + 2; i++){
            for (int j = pos.getX() - 3; j <= pos.getX() + 3; j++){
                for (int k = pos.getZ() -3; k <= pos.getZ() + 3; k++){
                    BlockPos targetPos = pos.add(i, j, k);
                    if (world.getBlockState(targetPos).isOf(ModBlocks.OPEN_EYEBLOSSOM) && !world.getBlockState(targetPos).get(EyeBlossomFlowerBlock.STATUS)){
                        target.add(targetPos);
                    }
                }
            }
        }
        for (BlockPos blockPos : target){
            world.setBlockState(blockPos, ModBlocks.CLOSED_EYEBLOSSOM.getDefaultState(), Block.NOTIFY_ALL);
        }
    }
}

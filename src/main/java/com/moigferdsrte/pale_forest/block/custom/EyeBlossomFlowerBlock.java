package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EyeBlossomFlowerBlock extends FlowerBlock {

    public static final BooleanProperty STATUS = Properties.LIT;

    public EyeBlossomFlowerBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(STATUS, false));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        this.impact(world, pos);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STATUS);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.updateState(state, world, pos);
        super.randomDisplayTick(state, world, pos, random);
    }

    private void updateState(BlockState state, World world, BlockPos pos) {
        long timeOfDay = world.getTimeOfDay() %24000;
        if (timeOfDay <= 12000 && state.get(STATUS)){
            world.setBlockState(pos, state.with(STATUS, false), Block.NOTIFY_ALL);
        }else if (timeOfDay > 12000 && !state.get(STATUS)){
            world.setBlockState(pos, state.with(STATUS, true), Block.NOTIFY_ALL);
        }
    }

    private void impact(World world, BlockPos pos){
        List<BlockPos> target = new ArrayList<>();

        for (int i = pos.getY() - 2; i <= pos.getY() + 2; i++){
            for (int j = pos.getX() - 3; j <= pos.getX() + 3; j++){
                for (int k = pos.getZ() -3; k <= pos.getZ() + 3; k++){
                    BlockPos targetPos = pos.add(i, j, k);
                    if (world.getBlockState(targetPos).isOf(ModBlocks.CLOSED_EYEBLOSSOM)){
                        target.add(targetPos);
                    }
                }
            }
        }
        for (BlockPos blockPos : target){
            world.setBlockState(blockPos, ModBlocks.OPEN_EYEBLOSSOM.getDefaultState(), Block.NOTIFY_ALL);
        }
    }
}

package com.moigferdsrte.pale_forest.block.custom;

import com.mojang.serialization.MapCodec;
import java.util.function.BiFunction;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ModdedFlowerbedBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<ModdedFlowerbedBlock> CODEC = createCodec(ModdedFlowerbedBlock::new);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty FLOWER_AMOUNT = Properties.FLOWER_AMOUNT;
    private static final BiFunction<Direction, Integer, VoxelShape> FACING_AND_AMOUNT_TO_SHAPE = Util.memoize(
            (facing, flowerAmount) -> {
                VoxelShape[] voxelShapes = new VoxelShape[]{
                        Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
                        Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
                        Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
                        Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
                };
                VoxelShape voxelShape = VoxelShapes.empty();

                for (int i = 0; i < flowerAmount; i++) {
                    int j = Math.floorMod(i - facing.getHorizontal(), 4);
                    voxelShape = VoxelShapes.union(voxelShape, voxelShapes[j]);
                }

                return voxelShape.asCuboid();
            }
    );

    @Override
    public MapCodec<ModdedFlowerbedBlock> getCodec() {
        return CODEC;
    }

    public ModdedFlowerbedBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FLOWER_AMOUNT, 4));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(FLOWER_AMOUNT) < 4 || super.canReplace(state, context);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FACING_AND_AMOUNT_TO_SHAPE.apply(state.get(FACING), state.get(FLOWER_AMOUNT));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return blockState.isOf(this)
                ? blockState.with(FLOWER_AMOUNT, Math.min(4, blockState.get(FLOWER_AMOUNT) + 1))
                : this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FLOWER_AMOUNT);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            if (state.get(FLOWER_AMOUNT) > 1) {
                world.setBlockState(pos, state.with(FLOWER_AMOUNT, state.get(FLOWER_AMOUNT) - 1));
            }else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            world.playSound(null, pos, SoundEvents.BLOCK_BIG_DRIPLEAF_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ItemActionResult.SUCCESS;
        }
        else return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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
        int i = (Integer)state.get(FLOWER_AMOUNT);
        if (i < 4) {
            world.setBlockState(pos, state.with(FLOWER_AMOUNT, i + 1), Block.NOTIFY_LISTENERS);
        } else {
            dropStack(world, pos, new ItemStack(this));
        }
    }
}

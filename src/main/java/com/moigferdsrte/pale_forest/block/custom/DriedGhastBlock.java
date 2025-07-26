package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class DriedGhastBlock extends HorizontalFacingBlock implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty MOIST = IntProperty.of("hydration", 0, 3);


    public DriedGhastBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(MOIST, 0).with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case Direction.NORTH -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125),
                        VoxelShapes.cuboid(0, 0, 0.3125, 0.1875, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0, 0, 0.5625, 0.1875, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.8125, 0, 0.3125, 1, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0.8125, 0, 0.5625, 1, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.5625, 0, 0.8125, 0.6875, 0.0625, 1),
                        VoxelShapes.cuboid(0.3125, 0, 0.8125, 0.4375, 0.0625, 1)
                );

            }
            case Direction.EAST -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125),
                        VoxelShapes.cuboid(0, 0, 0.3125, 0.1875, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0, 0, 0.5625, 0.1875, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.5625, 0, 0, 0.6875, 0.0625, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0, 0, 0.4375, 0.0625, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0, 0.8125, 0.4375, 0.0625, 1),
                        VoxelShapes.cuboid(0.5625, 0, 0.8125, 0.6875, 0.0625, 1)
                );
            }
            case WEST -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125),
                        VoxelShapes.cuboid(0.8125, 0, 0.3125, 1, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0.8125, 0, 0.5625, 1, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.5625, 0, 0, 0.6875, 0.0625, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0, 0, 0.4375, 0.0625, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0, 0.8125, 0.4375, 0.0625, 1),
                        VoxelShapes.cuboid(0.5625, 0, 0.8125, 0.6875, 0.0625, 1)
                );
            }
            case SOUTH -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125),
                        VoxelShapes.cuboid(0, 0, 0.3125, 0.1875, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0, 0, 0.5625, 0.1875, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.8125, 0, 0.3125, 1, 0.0625, 0.4375),
                        VoxelShapes.cuboid(0.8125, 0, 0.5625, 1, 0.0625, 0.6875),
                        VoxelShapes.cuboid(0.5625, 0, 0, 0.6875, 0.0625, 0.1875),
                        VoxelShapes.cuboid(0.3125, 0, 0, 0.4375, 0.0625, 0.1875)
                );
            }
            default -> {
                return Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
            }
        }
    }

    @Override
    protected MapCodec<DriedGhastBlock> getCodec() {
        return createCodec(DriedGhastBlock::new);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        this.checkRecoverConditions(state, world, pos);
    }

    protected void checkRecoverConditions(BlockState state, WorldAccess world, BlockPos pos) {
        if (isInWater(state)) {
            world.scheduleBlockTick(pos, this, 160 + world.getRandom().nextInt(30));
        } else {
            world.scheduleBlockTick(pos, this, 150 + world.getRandom().nextInt(30));
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isReadyToRecover(state) && state.get(MOIST) >= 0) {
            world.playSound(null, pos, SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            world.setBlockState(pos, state.with(MOIST, this.getMoistState(state) + (isInWater(state) ? 1 : state.get (MOIST) == 0 ? 0 : (-1))), Block.NOTIFY_LISTENERS);
        } else if (this.isReadyToRecover(state)) {
            if (isInWater(state)) {
                world.playSound(null, pos, SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.breakBlock(pos, false);
                HappyGhastEntity ghast = ModEntities.HAPPY_GHAST.create(world);
                if (ghast != null) {
                    Vec3d vec3d = pos.toCenterPos();
                    ghast.setBaby(true);
                    ghast.refreshPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                    world.spawnEntity(ghast);
                }
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, state.with(MOIST, this.getMoistState(state) - 1), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(MOIST, 0).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }


    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        this.checkRecoverConditions(state, world, pos);
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (state.get(WATERLOGGED)) {
                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
        }

        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP);
    }

    public int getMoistState(BlockState state) {
        return state.get(MOIST);
    }

    private boolean isReadyToRecover(BlockState state) {
        return this.getMoistState(state) == 3;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, MOIST, FACING);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected static boolean isInWater(BlockState state) {
        return state.get(WATERLOGGED);
    }
}

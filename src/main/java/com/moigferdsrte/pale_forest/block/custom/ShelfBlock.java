package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.entity.block.ShelfBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;

public class ShelfBlock extends BlockWithEntity implements Waterloggable {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty ALIGN = BooleanProperty.of("align_items_to_bottom");
    public static final BooleanProperty LEFT = BooleanProperty.of("left");
    public static final BooleanProperty RIGHT = BooleanProperty.of("right");


    public ShelfBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(POWERED, false)
                .with(LEFT, false)
                .with(RIGHT, false)
                .with(ALIGN, false)
                .with(WATERLOGGED, false));
    }

    private OptionalInt getSlotForHitPos(BlockHitResult hit, BlockState state) {
        return getHitPos(hit, state.get(HorizontalFacingBlock.FACING)).map(hitPos -> {
            int j = getColumn(hitPos.x);
            return OptionalInt.of(j);
        }).orElseGet(OptionalInt::empty);
    }

    private static int getColumn(float x) {
        if (x < 0.375F) {
            return 0;
        } else {
            return x < 0.6875F ? 1 : 2;
        }
    }

    private static Optional<Vec2f> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getSide();
        if (facing != direction) {
            return Optional.empty();
        } else {
            BlockPos blockPos = hit.getBlockPos().offset(direction);
            Vec3d vec3d = hit.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            double d = vec3d.getX();
            double e = vec3d.getY();
            double f = vec3d.getZ();

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2f((float)(1.0 - d), (float)e));
                case SOUTH -> Optional.of(new Vec2f((float)d, (float)e));
                case WEST -> Optional.of(new Vec2f((float)f, (float)e));
                case EAST -> Optional.of(new Vec2f((float)(1.0 - f), (float)e));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof ShelfBlockEntity shelfBlockEntity) {
            OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
            if (optionalInt.isEmpty()) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            ItemStack itemStack = player.getStackInHand(hand);
            if (!world.isClient && shelfBlockEntity.addItem(player, itemStack, optionalInt.getAsInt())) {
                world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                return ItemActionResult.SUCCESS;
            }
            return ItemActionResult.CONSUME;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ShelfBlockEntity shelfBlockEntity) {
                ItemScatterer.spawn(world, pos, shelfBlockEntity.getItemsStored());
            }
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0.8125, 1, 1, 1),
                        VoxelShapes.cuboid(0, 0.75, 0.6875, 1, 1, 0.8125),
                        VoxelShapes.cuboid(0, 0, 0.6875, 1, 0.25, 0.8125)
                );
            }
            case SOUTH ->  {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.1875),
                        VoxelShapes.cuboid(0, 0.75, 0.1875, 1, 1, 0.3125),
                        VoxelShapes.cuboid(0, 0, 0.1875, 1, 0.25, 0.3125)
                );
            }
            case WEST -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0.8125, 0, 0, 1, 1, 1),
                        VoxelShapes.cuboid(0.6875, 0.75, 0, 0.8125, 1, 1),
                        VoxelShapes.cuboid(0.6875, 0, 0, 0.8125, 0.25, 1)
                );
            }
            case EAST -> {
                return VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 0.1875, 1, 1),
                        VoxelShapes.cuboid(0.1875, 0.75, 0, 0.3125, 1, 1),
                        VoxelShapes.cuboid(0.1875, 0, 0, 0.3125, 0.25, 1)
                );
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = state.get(POWERED);
            boolean hasPower = world.isReceivingRedstonePower(pos);
            if (bl != hasPower) {
                if (bl) {
                    world.scheduleBlockTick(pos, this, 4);
                } else {
                    BlockState newState = state.with(POWERED, true);
                    world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS);
                    updateConnections(world, pos, newState);
                }
            } else if (bl) {
                updateConnections(world, pos, state);
            }
        }
    }


    private void updateConnections(World world, BlockPos pos, BlockState state) {
        if (!state.get(POWERED)) {
            return;
        }

        Direction facing = state.get(FACING);
        Direction leftDir = facing.rotateYCounterclockwise();
        Direction rightDir = facing.rotateYClockwise();

        BlockPos leftPos = pos.offset(leftDir);
        BlockPos rightPos = pos.offset(rightDir);

        boolean leftConnected = isShelfBlock(world, leftPos, facing);
        boolean rightConnected = isShelfBlock(world, rightPos, facing);

        BlockState newState = state
                .with(LEFT, leftConnected)
                .with(RIGHT, rightConnected);

        if (!state.equals(newState)) {
            world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS);
        }
    }

    private boolean isShelfBlock(BlockView world, BlockPos pos, Direction facing) {
        BlockState neighborState = world.getBlockState(pos);
        if (neighborState.getBlock() instanceof ShelfBlock) {
            return neighborState.get(FACING) == facing;
        }
        return false;
    }


    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            BlockState newState = state.with(POWERED, false)
                    .with(LEFT, false)
                    .with(RIGHT, false);
            world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (state.get(WATERLOGGED)) {
                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof ShelfBlockEntity shelfBlockEntity ? shelfBlockEntity.getItemsStored().size() : 0;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ShelfBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, WATERLOGGED, LEFT, RIGHT, ALIGN);
    }
}

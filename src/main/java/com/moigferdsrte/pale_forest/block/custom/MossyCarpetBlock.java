package com.moigferdsrte.pale_forest.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MossyCarpetBlock extends Block implements Fertilizable {
    public static final MapCodec<MossyCarpetBlock> CODEC = createCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = Properties.BOTTOM;
    private static final EnumProperty<WallShape> NORTH = Properties.NORTH_WALL_SHAPE;
    private static final EnumProperty<WallShape> EAST = Properties.EAST_WALL_SHAPE;
    private static final EnumProperty<WallShape> SOUTH = Properties.SOUTH_WALL_SHAPE;
    private static final EnumProperty<WallShape> WEST = Properties.WEST_WALL_SHAPE;
    private static final Map<Direction, EnumProperty<WallShape>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
        Util.make(Maps.newEnumMap(Direction.class), enumMap -> {
            enumMap.put(Direction.NORTH, NORTH);
            enumMap.put(Direction.EAST, EAST);
            enumMap.put(Direction.SOUTH, SOUTH);
            enumMap.put(Direction.WEST, WEST);
        })
    );
    private static final VoxelShape DOWN_AABB = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_AABB = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHORT_AABB = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);
    private static final VoxelShape EAST_SHORT_AABB = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    private static final VoxelShape NORTH_SHORT_AABB = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
    private static final VoxelShape SOUTH_SHORT_AABB = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    public MossyCarpetBlock(Settings properties) {
        super(properties);
        this.setDefaultState(
            this.getStateManager()
                .getDefaultState()
                .with(BASE, true)
                .with(NORTH, WallShape.NONE)
                .with(EAST, WallShape.NONE)
                .with(SOUTH, WallShape.NONE)
                .with(WEST, WallShape.NONE)
        );
        this.shapesCache = ImmutableMap.copyOf(
            this.getStateManager()
                .getStates()
                .stream()
                .collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
        );
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(BASE) ? DOWN_AABB : VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    private static VoxelShape calculateShape(BlockState state) {
        VoxelShape shape = VoxelShapes.empty();
        if (state.get(BASE)) shape = DOWN_AABB;

        shape = switch (state.get(NORTH)) {
            case NONE -> shape;
            case LOW -> VoxelShapes.union(shape, NORTH_SHORT_AABB);
            case TALL -> VoxelShapes.union(shape, NORTH_AABB);
        };

        shape = switch (state.get(SOUTH)) {
            case NONE -> shape;
            case LOW -> VoxelShapes.union(shape, SOUTH_SHORT_AABB);
            case TALL -> VoxelShapes.union(shape, SOUTH_AABB);
        };

        shape = switch (state.get(EAST)) {
            case NONE -> shape;
            case LOW -> VoxelShapes.union(shape, EAST_SHORT_AABB);
            case TALL -> VoxelShapes.union(shape, EAST_AABB);
        };

        shape = switch (state.get(WEST)) {
            case NONE -> shape;
            case LOW -> VoxelShapes.union(shape, WEST_SHORT_AABB);
            case TALL -> VoxelShapes.union(shape, WEST_AABB);
        };

        return shape.isEmpty() ? VoxelShapes.fullCube() : shape;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState floorState = world.getBlockState(pos.down());
        return state.get(BASE)
                ? !floorState.isAir()
                : floorState.isOf(this)
                && floorState.get(BASE);
    }

    private static boolean hasFaces(BlockState state) {
        if (state.get(BASE)) {
            return true;
        }
        return PROPERTY_BY_DIRECTION.values()
            .stream()
            .anyMatch(property -> state.get(property) != WallShape.NONE);
    }

    private static boolean canSupportAtFace(BlockView level, BlockPos pos, Direction direction) {
        BlockPos adjacent = pos.offset(direction);
        BlockState adjacentState = level.getBlockState(adjacent);
        return direction != Direction.UP && MultifaceGrowthBlock.canGrowOn(level, direction, adjacent, adjacentState);
    }

    private static BlockState getUpdatedState(BlockState state, BlockView level, BlockPos pos, boolean flag) {
        BlockState aboveState = null;
        BlockState belowState = null;
        flag |= state.get(BASE);

        for (Direction direction : Direction.Type.HORIZONTAL) {
            EnumProperty<WallShape> property = getPropertyForFace(direction);
            WallShape wallSide = canSupportAtFace(level, pos, direction)
                ? (flag ? WallShape.LOW : state.get(property))
                : WallShape.NONE;

            if (wallSide == WallShape.LOW) {
                if (aboveState == null) {
                    aboveState = level.getBlockState(pos.up());
                }

                if (aboveState.isOf(ModBlocks.PALE_MOSS_CARPET)
                    && aboveState.get(property) != WallShape.NONE
                    && !aboveState.get(BASE)
                ) {
                    wallSide = WallShape.TALL;
                }

                if (!state.get(BASE)) {
                    if (belowState == null) {
                        belowState = level.getBlockState(pos.down());
                    }

                    if (belowState.isOf(ModBlocks.PALE_MOSS_CARPET) && belowState.get(property) == WallShape.NONE) {
                        wallSide = WallShape.NONE;
                    }
                }
            }

            state = state.with(property, wallSide);
        }

        return state;
    }

    public static void placeAt(WorldAccess level, BlockPos pos, Random random, int flag) {
        BlockState base = ModBlocks.PALE_MOSS_CARPET.getDefaultState();
        BlockState updatedState = getUpdatedState(base, level, pos, true);
        level.setBlockState(pos, updatedState, flag);
        BlockState topperState = createTopperWithSideChance(level, pos, random::nextBoolean);

        if (!topperState.isAir()) {
            level.setBlockState(pos.up(), topperState, flag);
            BlockState reUpdatedState = getUpdatedState(updatedState, level, pos, true);
            level.setBlockState(pos, reUpdatedState, flag);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient()) {
            Random random = world.getRandom();
            BlockState topperState = createTopperWithSideChance(world, pos, random::nextBoolean);
            if (!topperState.isAir()) {
                world.setBlockState(pos.up(), topperState, 3);
            }
        }
    }

    private static BlockState createTopperWithSideChance(BlockView level, BlockPos pos, BooleanSupplier flag) {
        BlockPos above = pos.up();
        BlockState aboveState = level.getBlockState(above);
        boolean isCarpet = aboveState.isOf(ModBlocks.PALE_MOSS_CARPET);

        if ((!isCarpet || !aboveState.get(BASE))
            && (isCarpet || aboveState.isReplaceable())
        ) {
            BlockState baselessCarpet = ModBlocks.PALE_MOSS_CARPET.getDefaultState().with(BASE, false);
            BlockState updatedState = getUpdatedState(baselessCarpet, level, pos.up(), true);

            for (Direction direction : Direction.Type.HORIZONTAL) {
                EnumProperty<WallShape> property = getPropertyForFace(direction);
                if (updatedState.get(property) != WallShape.NONE && !flag.getAsBoolean()) {
                    updatedState = updatedState.with(property, WallShape.NONE);
                }
            }

            return hasFaces(updatedState) && updatedState != aboveState
                ? updatedState
                : Blocks.AIR.getDefaultState();
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getUpdatedState(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos(), true);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            BlockState updatedState = getUpdatedState(state, world, pos, false);
            return !hasFaces(updatedState) ? Blocks.AIR.getDefaultState() : updatedState;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BASE, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state.with(NORTH, state.get(SOUTH))
                    .with(EAST, state.get(WEST))
                    .with(SOUTH, state.get(NORTH))
                    .with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 -> state.with(NORTH, state.get(EAST))
                    .with(EAST, state.get(SOUTH))
                    .with(SOUTH, state.get(WEST))
                    .with(WEST, state.get(NORTH));
            case CLOCKWISE_90 -> state.with(NORTH, state.get(WEST))
                    .with(EAST, state.get(NORTH))
                    .with(SOUTH, state.get(EAST))
                    .with(WEST, state.get(SOUTH));
            default -> state;
        };
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Nullable
    public static EnumProperty<WallShape> getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }


    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(BASE) && !createTopperWithSideChance(world, pos, () -> true).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState topperState = createTopperWithSideChance(world, pos, () -> true);
        if (!topperState.isAir()) {
            world.setBlockState(pos.up(), topperState, 3);
        }
    }
}
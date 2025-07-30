package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.entity.block.CopperGolemStatueBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CopperGolemStatueBlock extends BlockWithEntity implements Oxidizable, Waterloggable {

    public static final MapCodec<CopperGolemStatueBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Oxidizable.OxidationLevel.CODEC.fieldOf("weathering_state").forGetter(Degradable::getDegradationLevel), createSettingsCodec())
                    .apply(instance, CopperGolemStatueBlock::new)
    );

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final EnumProperty<CopperGolemStatuePose> POSE = EnumProperty.of("copper_golem_pose", CopperGolemStatuePose.class);

    private final Oxidizable.OxidationLevel oxidationLevel;

    public CopperGolemStatueBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
        this.setDefaultState(this.getDefaultState().with(POSE, CopperGolemStatuePose.STANDING));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Registries.BLOCK.getId(state.getBlock()).getPath().contains("waxed_")) {
            super.randomTick(state, world, pos, random);
            return;
        }
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        return blockState != null
                ? withWaterloggedState(ctx.getWorld(), ctx.getBlockPos(), blockState.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()))
                : null;
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        return state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, world.isWater(pos)) : state;
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
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperGolemStatueBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POSE, FACING, WATERLOGGED);super.appendProperties(builder);
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}

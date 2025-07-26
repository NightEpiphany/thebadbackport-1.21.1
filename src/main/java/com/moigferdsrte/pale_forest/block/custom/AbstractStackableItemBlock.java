package com.moigferdsrte.pale_forest.block.custom;

import com.moigferdsrte.pale_forest.poi.BlockPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractStackableItemBlock extends AbstractItemBlock {

    public static final IntProperty PIECES = IntProperty.of("fragments", 1, 9);

    public AbstractStackableItemBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(PIECES, 1));
    }

    private static int getMaxItemCount() {
        return 9;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(PIECES) == getMaxItemCount())
            return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;

        if (shouldAddItem(player.getStackInHand(hand), state)) {
            ActionResult result = BlockPlacer.place(state.getBlock(), player, hand, hit);

            if (result.isAccepted()) {
                return ItemActionResult.CONSUME;
            }
            else {
                return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (state.get(PIECES) == getMaxItemCount()) return false;
        if (shouldAddItem(context.getStack(), state)) {
            return true;
        }

        return super.canReplace(state, context);
    }

    private boolean shouldAddItem(ItemStack heldItemStack, BlockState state) {
        boolean sameItem = heldItemStack.isOf(state.getBlock().asItem());
        return sameItem && this.getPiece() < getMaxItemCount();
    }

    public int getPiece() {
        return this.stateManager.getDefaultState().get(PIECES);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            if (blockState.getBlock().getDefaultState().get(PIECES) < getMaxItemCount())
                return blockState.cycle(PIECES);
        }

        return super.getPlacementState(ctx);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PIECES);
    }
}

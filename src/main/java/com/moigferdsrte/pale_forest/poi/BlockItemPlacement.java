package com.moigferdsrte.pale_forest.poi;

import com.moigferdsrte.pale_forest.block.custom.AbstractStackableItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class BlockItemPlacement extends BlockItem {
    public BlockItemPlacement(Block block, Settings settings) {
        super(block, settings);
    }

    public boolean stackedSettings(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getBlockPos());
        if (state.getBlock() instanceof AbstractStackableItemBlock abstractStackableItemBlock) {
            return abstractStackableItemBlock.getStateManager().getDefaultState().get(AbstractStackableItemBlock.PIECES) != 9;
        }else return true;
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (Objects.requireNonNull(context.getPlayer()).isSneaking() && this.stackedSettings(context))
            return super.place(context);
        else return ActionResult.PASS;
    }
}

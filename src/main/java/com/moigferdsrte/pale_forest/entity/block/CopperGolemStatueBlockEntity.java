package com.moigferdsrte.pale_forest.entity.block;

import com.moigferdsrte.pale_forest.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CopperGolemStatueBlockEntity extends BlockEntity {
    public CopperGolemStatueBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPER_GOLEM_STATUE, pos, state);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return super.toUpdatePacket();
    }
}

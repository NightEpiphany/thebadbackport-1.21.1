package com.moigferdsrte.pale_forest.entity.block;


import com.moigferdsrte.pale_forest.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class StarCopperGolemStatueBlockEntity extends BlockEntity {
    public StarCopperGolemStatueBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAR_COPPER_GOLEM_STATUE, pos, state);
    }

    private void sync() {
        if (world instanceof ServerWorld serverWorld) {
            BlockEntityUpdateS2CPacket packet = BlockEntityUpdateS2CPacket.create(this);
            for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                if (player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64) {
                    player.networkHandler.sendPacket(packet);
                }
            }
        }
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        this.sync();
        return super.toUpdatePacket();
    }
}

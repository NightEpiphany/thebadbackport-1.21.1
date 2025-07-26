package com.moigferdsrte.pale_forest.entity.block;

import com.moigferdsrte.pale_forest.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ShelfBlockEntity extends BlockEntity implements Clearable {

    private final DefaultedList<ItemStack> itemsStored = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHELF, pos, state);
    }

    public DefaultedList<ItemStack> getItemsStored() {
        return itemsStored;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.itemsStored.clear();
        Inventories.readNbt(nbt, this.itemsStored, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.itemsStored, registryLookup);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.itemsStored, true, registryLookup);
        return nbtCompound;
    }

    public boolean addItem(@Nullable LivingEntity user, ItemStack stack, int slot) {
            ItemStack itemStack = this.itemsStored.get(slot);
        assert world != null;
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 0.2F, ((world.random.nextFloat() - world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            if (itemStack.isEmpty()) {
                this.itemsStored.set(slot, stack.splitUnlessCreative(stack.getCount(), user));
                assert this.world != null;
                this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(user, this.getCachedState()));
                this.sync();
                this.updateListeners();
                return true;
            } else if (user instanceof PlayerEntity player) {
                this.itemsStored.set(slot, stack.splitUnlessCreative(stack.getCount(), user));
                assert this.world != null;
                player.equipStack(EquipmentSlot.MAINHAND, itemStack);
                this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(user, this.getCachedState()));
                this.sync();
                this.updateListeners();
                return true;
            }
        return false;
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
        return super.toUpdatePacket();
    }

    private void updateListeners() {
        this.markDirty();
        assert this.getWorld() != null;
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public void clear() {
        this.itemsStored.clear();
    }
}

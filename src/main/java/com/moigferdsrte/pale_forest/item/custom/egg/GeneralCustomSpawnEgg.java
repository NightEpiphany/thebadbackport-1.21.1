package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.Spawner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public abstract class GeneralCustomSpawnEgg extends SpawnEggItem {

    protected final EntityType<?> entityType;

    public GeneralCustomSpawnEgg(EntityType<? extends MobEntity> type, Settings settings) {
        super(type, 0xFFFFFF, 0xFFFFFF, settings);
        this.entityType = type;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);

            if (world.getBlockEntity(blockPos) instanceof Spawner spawner) {
                EntityType<?> entityType = this.getEntityType(itemStack);
                spawner.setEntityType(entityType, world.getRandom());
                world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.decrement(1);
            } else {
                BlockPos spawnPos = blockState.getCollisionShape(world, blockPos).isEmpty()
                        ? blockPos
                        : blockPos.offset(direction);

                MobEntity entity = (MobEntity) this.entityType.create(serverWorld);
                if (entity != null) {
                    entity.refreshPositionAndAngles(
                            spawnPos.getX() + 0.5,
                            spawnPos.getY(),
                            spawnPos.getZ() + 0.5,
                            serverWorld.random.nextFloat() * 360.0F,
                            0.0F
                    );


                    this.operation(entity);

                    if (serverWorld.spawnEntity(entity)) {
                        itemStack.decrement(1);
                        world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                    }
                }
            }
            return ActionResult.CONSUME;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld serverWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(itemStack);
            } else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {

                MobEntity entity = (MobEntity) this.entityType.create(serverWorld);
                if (entity == null) {
                    return TypedActionResult.pass(itemStack);
                } else {

                    entity.refreshPositionAndAngles(
                            blockPos.getX() + 0.5,
                            blockPos.getY(),
                            blockPos.getZ() + 0.5,
                            serverWorld.random.nextFloat() * 360.0F,
                            0.0F
                    );


                    this.operation(entity);



                    if (serverWorld.spawnEntity(entity)) {
                        itemStack.decrementUnlessCreative(1, user);
                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
                        return TypedActionResult.consume(itemStack);
                    }
                }
            }
            return TypedActionResult.fail(itemStack);
        }
    }

    public abstract void operation(MobEntity entity);

    @Override
    public int getColor(int tintIndex) {
        return super.getColor(0xFFFFFF);
    }
}

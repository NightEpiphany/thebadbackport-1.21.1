package com.moigferdsrte.pale_forest.api.leash;

import com.blackgear.platform.common.integration.MobInteraction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LeashIntegration implements MobInteraction {
    @Override
    public ActionResult onInteract(PlayerEntity player, Entity entity, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!entity.getWorld().isClient() && player.shouldCancelInteraction() && entity instanceof Leashable leashable && leashable.canBeLeashed() && entity.isAlive()) {
            if (!(entity instanceof LivingEntity living && living.isBaby())) {
                List<Leashable> nearbyMobs = LeashExtension.leashableInArea(entity, l -> l.getLeashHolder() == player);

                if (!nearbyMobs.isEmpty()) {
                    boolean attachedAny = false;

                    for (Leashable target : nearbyMobs) {
                        if (((LeashExtension) target).canHaveALeashAttachedTo(entity)) {
                            target.attachLeash(entity, true);
                            attachedAny = true;
                        }
                    }

                    if (attachedAny) {
                        entity.getWorld().emitGameEvent(GameEvent.ENTITY_ACTION, entity.getBlockPos(), GameEvent.Emitter.of(player));
                        entity.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1, 1);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        // Shear off all leash connections
        if (stack.isOf(Items.SHEARS) && this.shearOffAllLeashConnections(entity, player)) {
            stack.damage(1, player, LivingEntity.getSlotForHand(hand));
            return ActionResult.SUCCESS;
        }

        if (entity.isAlive() && entity instanceof Leashable leashable) {
            // Drop leash
            if (leashable.getLeashHolder() == player) {
                if (!entity.getWorld().isClient()) {
                    leashable.detachLeash(true, !player.isCreative());
                    entity.getWorld().emitGameEvent(GameEvent.ENTITY_INTERACT, entity.getPos(), GameEvent.Emitter.of(player));
                    entity.playSound(SoundEvents.ENTITY_LEASH_KNOT_BREAK, 1, 1);
                }

                return ActionResult.SUCCESS;
            }

            // Attach a new leash
            if (stack.isOf(Items.LEAD) && !(leashable.getLeashHolder() instanceof PlayerEntity)) {
                if (!entity.getWorld().isClient() && ((LeashExtension) leashable).canHaveALeashAttachedTo(player)) {
                    if (leashable.isLeashed()) {
                        leashable.detachLeash(true, true);
                    }

                    leashable.attachLeash(player, true);
                    entity.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1, 1);

                    if (!player.isCreative()) stack.decrement(1);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private boolean shearOffAllLeashConnections(Entity entity, PlayerEntity player) {
        boolean sheared = dropAllLeashConnections(entity, player);
        if (sheared && entity.getWorld() instanceof ServerWorld server) {
            server.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, player != null ? player.getSoundCategory() : entity.getSoundCategory());
        }

        return sheared;
    }

    public static boolean dropAllLeashConnections(Entity entity, @Nullable PlayerEntity player) {
        List<Leashable> leashed = LeashExtension.leashableLeashedTo(entity);
        boolean dropConnections = !leashed.isEmpty();

        if (entity instanceof Leashable leashable && leashable.isLeashed()) {
            leashable.detachLeash(true, true);
            dropConnections = true;
        }

        for (Leashable leashable : leashed) {
            leashable.detachLeash(true, true);
        }

        if (dropConnections) {
            entity.emitGameEvent(GameEvent.SHEAR, player);
            return true;
        } else {
            return false;
        }
    }
}
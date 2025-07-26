package com.moigferdsrte.pale_forest.api.leash;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface LeashExtension extends Leashable {
    Map<Predicate<Entity>, Function<Entity, Vec3d[]>> QUAD_LEASH_OFFSETS = Util.make(() -> {
        ImmutableMap.Builder<Predicate<Entity>, Function<Entity, Vec3d[]>> offsets = new ImmutableMap.Builder<>();
        offsets.put(entity -> entity instanceof BoatEntity, entity -> createQuadLeashOffsets(entity, 0.0, 0.64, 0.382, 0.88));
        offsets.put(entity -> entity instanceof CamelEntity, entity -> createQuadLeashOffsets(entity, 0.02, 0.48, 0.25, 0.82));
        offsets.put(entity -> entity instanceof AbstractHorseEntity, entity -> createQuadLeashOffsets(entity, 0.04, 0.52, 0.23, 0.87));
        offsets.put(entity -> entity instanceof AbstractDonkeyEntity, entity -> createQuadLeashOffsets(entity, 0.04, 0.41, 0.18, 0.73));
        offsets.put(entity -> entity instanceof SnifferEntity, entity -> createQuadLeashOffsets(entity, -0.01, 0.63, 0.38, 1.15));
        return offsets.build();
    });

    Vec3d AXIS_SPECIFIC_ELASTICITY = new Vec3d(0.8, 0.2, 0.8);
    List<Vec3d> ENTITY_ATTACHMENT_POINT = ImmutableList.of(new Vec3d(0.0, 0.5, 0.5));
    List<Vec3d> LEASHER_ATTACHMENT_POINT = ImmutableList.of(new Vec3d(0.0, 0.5, 0.0));
    List<Vec3d> SHARED_QUAD_ATTACHMENT_POINTS = ImmutableList.of(
        new Vec3d(-0.5, 0.5, 0.5),
        new Vec3d(-0.5, 0.5, -0.5),
        new Vec3d(0.5, 0.5, -0.5),
        new Vec3d(0.5, 0.5, 0.5)
    );

    default boolean canHaveALeashAttachedTo(Entity target) {
        if (this == target) {
            return false;
        } else {
            return this.leashDistanceTo(target) <= this.leashSnapDistance() && ((Leashable) this).canBeLeashed();
        }
    }

    default double leashDistanceTo(Entity entity) {
        return entity.getBoundingBox().getCenter().distanceTo(((Entity) this).getBoundingBox().getCenter());
    }

    default void onElasticLeashPull() {
        ((Entity) this).limitFallDistance();
    }

    default double leashSnapDistance() {
        return 12.0;
    }

    default double leashElasticDistance() {
        return 6.0;
    }

    static <E extends Entity & Leashable> float angularFriction(E entity) {
        if (entity.isOnGround()) {
            return entity.getWorld().getBlockState(entity.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91F;
        } else {
            return entity.isInFluid() ? 0.8F : 0.91F;
        }
    }

    default void whenLeashedTo(Entity entity) {
        if (entity instanceof LeashExtension extension) extension.notifyLeashHolder((Leashable) this);
    }

    default void notifyLeashHolder(Leashable leashable) {

    }

    default void resetAngularMomentum() {
        Leashable.LeashData data = ((Leashable) this).getLeashData();
        if (data != null && ((Object) data) instanceof LeashDataExtension extension) {
            extension.setAngularMomentum(0.0);
        }
    }

    default boolean checkElasticInteractions(Entity entity, Leashable.LeashData data) {
        if (((Entity) this).getControllingPassenger() instanceof PlayerEntity) return false;

        boolean supportQuadLeash = entity instanceof LeashExtension holder && holder.supportQuadLeashAsHolder() && this.supportQuadLeash();
        List<Wrench> wrenches = computeElasticInteraction(
            (Entity & Leashable & LeashExtension) this,
            entity,
            supportQuadLeash ? SHARED_QUAD_ATTACHMENT_POINTS : ENTITY_ATTACHMENT_POINT,
            supportQuadLeash ? SHARED_QUAD_ATTACHMENT_POINTS : LEASHER_ATTACHMENT_POINT
        );

        if (wrenches.isEmpty()) {
            return false;
        } else {
            Wrench wrench = Wrench.accumulate(wrenches).scale(supportQuadLeash ? 0.25 : 1.0);
            LeashDataExtension extension = (LeashDataExtension) (Object) data;
            extension.setAngularMomentum(extension.angularMomentum() + 10.0 * wrench.torque());
            Vec3d offset = getHolderMovement(entity).subtract(getKnownMovement((Entity) this));
            ((Entity) this).addVelocity(wrench.force().multiply(AXIS_SPECIFIC_ELASTICITY).add(offset.multiply(0.11)));
            return true;
        }
    }

    static Vec3d getHolderMovement(Entity entity) {
        return entity instanceof MobEntity mob && mob.isAiDisabled() ? Vec3d.ZERO : getKnownMovement(entity);
    }

    static Vec3d getKnownMovement(Entity entity) {
        Entity passenger = entity.getControllingPassenger();
        if (passenger instanceof PlayerEntity player) {
            if (entity.isAlive()) {
                return player.getVelocity();
            }
        }

        return entity.getVelocity();
    }

    static <E extends Entity & Leashable & LeashExtension> List<Wrench> computeElasticInteraction(E entity, Entity holder, List<Vec3d> attachmentPoints, List<Vec3d> holderAttachmentPoints) {
        double elasticDistance = entity.leashElasticDistance();
        Vec3d entityMovement = getHolderMovement(entity);
        float entityYaw = entity.getYaw() * (float) (Math.PI / 180.0);
        Vec3d entityDimensions = new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth());
        float holderYaw = holder.getYaw() * (float) (Math.PI / 180.0);
        Vec3d holderDimensions = new Vec3d(holder.getWidth(), holder.getHeight(), holder.getWidth());
        List<Wrench> wrenches = new ArrayList<>();

        for (int i = 0; i < attachmentPoints.size(); i++) {
            Vec3d entityOffset = attachmentPoints.get(i).multiply(entityDimensions).rotateY(-entityYaw);
            Vec3d entityPosition = entity.getPos().add(entityOffset);
            Vec3d holderOffset = holderAttachmentPoints.get(i).multiply(holderDimensions).rotateY(-holderYaw);
            Vec3d holderPosition = holder.getPos().add(holderOffset);
            computeDampenedSpringInteraction(holderPosition, entityPosition, elasticDistance, entityMovement, entityOffset).ifPresent(wrenches::add);
        }

        return wrenches;
    }

    private static Optional<Wrench> computeDampenedSpringInteraction(Vec3d holderPos, Vec3d entityPos, double threshold, Vec3d movement, Vec3d offset) {
        double distance = entityPos.distanceTo(holderPos);
        if (distance < threshold) {
            return Optional.empty();
        } else {
            Vec3d force = holderPos.subtract(entityPos).normalize().multiply(distance - threshold);
            double torque = Wrench.torqueFromForce(offset, force);
            boolean movingWithForce = movement.dotProduct(force) >= 0.0;
            if (movingWithForce) {
                force = force.multiply(0.3F);
            }

            return Optional.of(new Wrench(force, torque));
        }
    }

    default boolean supportQuadLeash() {
        Entity entity = (Entity) this;
        for (Predicate<Entity> filter : QUAD_LEASH_OFFSETS.keySet()) {
            if (filter.test(entity)) {
                return true;
            }
        }

        return false;
    }

    default boolean supportQuadLeashAsHolder() {
        return false;
    }

    default Vec3d[] getQuadLeashOffsets() {
        Entity entity = (Entity) this;
        for (Predicate<Entity> filter : QUAD_LEASH_OFFSETS.keySet()) {
            if (filter.test(entity)) {
                return QUAD_LEASH_OFFSETS.get(filter).apply(entity);
            }
        }

        return createQuadLeashOffsets((Entity) this, 0.0, 0.5, 0.5, 0.5);
    }

    default Vec3d[] getQuadLeashHolderOffsets() {
        return createQuadLeashOffsets((Entity) this, 0.0, 0.5, 0.5, 0.0);
    }

    static Vec3d[] createQuadLeashOffsets(Entity entity, double forwardOffset, double sideOffset, double widthOffset, double heightOffset) {
        float entityWidth = entity.getWidth();
        double forward = forwardOffset * (double) entityWidth;
        double side = sideOffset * (double) entityWidth;
        double width = widthOffset * (double) entityWidth;
        double height = heightOffset * (double) entity.getHeight();

        return new Vec3d[] {
            new Vec3d(-width, height, side + forward),
            new Vec3d(-width, height, -side + forward),
            new Vec3d(width, height, -side + forward),
            new Vec3d(width, height, side + forward)
        };
    }

    static List<Leashable> leashableLeashedTo(Entity entity) {
        return leashableInArea(entity, leashable -> leashable.getLeashHolder() == entity);
    }

    static List<Leashable> leashableInArea(Entity entity, Predicate<Leashable> filter) {
        return leashableInArea(entity.getWorld(), entity.getBoundingBox().getCenter(), filter);
    }

    static List<Leashable> leashableInArea(World level, Vec3d pos, Predicate<Leashable> filter) {
        Box area = Box.of(pos, 32.0, 32.0, 32.0);
        return level.getEntitiesByClass(Entity.class, area, entity -> entity instanceof Leashable leashable && filter.test(leashable))
            .stream()
            .map(Leashable.class::cast)
            .toList();
    }

    static float getPreciseBodyRotation(Entity entity, float partialTicks) {
        if (entity instanceof LivingEntity living) {
            return MathHelper.lerp(partialTicks, living.prevBodyYaw, living.bodyYaw);
        } else {
            return MathHelper.lerp(partialTicks, entity.prevYaw, entity.getYaw());
        }
    }

    record Wrench(Vec3d force, double torque) {
        static final Wrench ZERO = new Wrench(Vec3d.ZERO, 0.0);

        /**
         * Calculates torque from two vectors.
         */
        static double torqueFromForce(Vec3d position, Vec3d force) {
            return position.z * force.x - position.x * force.z;
        }

        /**
         * Combines multiple wrenches into a single resultant wrench.
         */
        public static Wrench accumulate(List<Wrench> wrenches) {
            if (wrenches.isEmpty()) return ZERO;

            double x = 0.0;
            double y = 0.0;
            double z = 0.0;
            double torque = 0.0;

            for (Wrench wrench : wrenches) {
                Vec3d force = wrench.force();
                x += force.x;
                y += force.y;
                z += force.z;
                torque += wrench.torque;
            }
            return new Wrench(new Vec3d(x, y, z), torque);
        }

        /**
         * Creates a new wrench by scaling the current one by the given factor.
         */
        public Wrench scale(double factor) {
            return new Wrench(this.force.multiply(factor), this.torque * factor);
        }
    }
}
package com.moigferdsrte.pale_forest.entity.custom;

import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.item.custom.HarnessItem;
import com.moigferdsrte.pale_forest.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.EnumSet;
import java.util.UUID;

public class HappyGhastEntity extends TameableEntity implements Angerable {

    private HappyGhastEntity.FlyRandomlyGoal flyRandomlyGoal;

    @Nullable
    private UUID angryAt;

    private static final int NO_HARNESS = 16;

    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);

    private static final TrackedData<Boolean> FESTIVE = DataTracker.registerData(HappyGhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Integer> HARNESS_COLOR = DataTracker.registerData(HappyGhastEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(HappyGhastEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(HappyGhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public HappyGhastEntity(EntityType<? extends HappyGhastEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 1;
        this.moveControl = new HappyGhastEntity.GhastMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createGhastAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return true;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        passenger.setYaw(this.getYaw());
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        Vec3d vec3d = this.getPassengerRidingPos(passenger);
        Vec3d vec3d2 = passenger.getVehicleAttachmentPos(this);
        positionUpdater.accept(passenger, vec3d.x - vec3d2.x, vec3d.y - vec3d2.y, vec3d.z - vec3d2.z);
        this.clampYaw(passenger);
    }

    private void clampYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float wrappedYaw = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float clampYaw = MathHelper.clamp(wrappedYaw, -120.0F, 120.0F);
        passenger.prevYaw += clampYaw - wrappedYaw;
        passenger.setYaw(passenger.getYaw() + clampYaw - wrappedYaw);
        passenger.setHeadYaw(passenger.getYaw());
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        Direction[] offsets = {direction, direction.rotateYClockwise(), direction.rotateYCounterclockwise(), direction.getOpposite()};
        for (Direction offset : offsets) {
            Vec3d vec3d = Dismounting.findRespawnPos(passenger.getType(), this.getWorld(), this.getBlockPos().offset(offset), false);
            if (vec3d != null) {
                return vec3d.add(0, 0.25, 0);
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.hasPassengers() || this.hasHarness()) {
            this.goalSelector.remove(this.flyRandomlyGoal);

        } else {
            this.flyRandomlyGoal = new FlyRandomlyGoal(this);
            this.goalSelector.add(2, this.flyRandomlyGoal);
        }
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        Vec2f vec2f = this.getControlledRotation(controllingPlayer);
        this.setRotation(vec2f.y, vec2f.x);
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_W)) this.move(MovementType.SELF, this.getVelocity());
        if (Screen.hasControlDown()) {
            this.move(MovementType.SELF, new Vec3d(0, -0.175F, 0));
        }
        if (Screen.hasAltDown()) {
            this.move(MovementType.SELF, new Vec3d(0, 0.175F, 0));
        }
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        return new Vec3d(0.0, 0.0, 1.0);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.isTamed() && this.getFirstPassenger() instanceof PlayerEntity playerEntity && playerEntity.isHolding(Items.SNOWBALL) && this.hasHarness()
                ? playerEntity
                : super.getControllingPassenger();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new TemptGoal(this, 2.0, stack -> stack.isOf(Items.SNOWBALL), false));
        this.goalSelector.add(3, new HappyGhastEntity.LookAtTargetGoal(this));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        //this.goalSelector.add(5, new HappyGhastEntity.ShootSnowBallGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HARNESS_COLOR,  NO_HARNESS);
        builder.add(ANGER_TIME, 0);
        builder.add(SHOOTING, false);
        builder.add(FESTIVE, false);
    }

    public boolean hasHarness() {
        return this.dataTracker.get(HARNESS_COLOR) < NO_HARNESS;
    }

    public DyeColor getHarnessColor() {
        return DyeColor.byId(this.dataTracker.get(HARNESS_COLOR));
    }

    public int getHarnessColorById() {
        return this.dataTracker.get(HARNESS_COLOR);
    }

    public void setHarnessColor(DyeColor color) {
       this.dataTracker.set(HARNESS_COLOR, color.getId());
    }

    public void setFestive(boolean isFestive) {
        this.dataTracker.set(FESTIVE, isFestive);
    }

    public boolean getFestive() {
        return this.dataTracker.get(FESTIVE);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SNOWBALL);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.HAPPY_GHAST.create(world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("harness_color", this.getHarnessColorById());
        nbt.putBoolean("festive", this.getFestive());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        boolean festive = nbt.getBoolean("festive");
        this.setFestive(festive);
        if (nbt.contains("harness_color", NbtElement.NUMBER_TYPE)) {
            if (nbt.getInt("harness_color") < NO_HARNESS) this.setHarnessColor(DyeColor.byId(nbt.getInt("harness_color")));
        }
    }

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8F));
            } else if (this.isInLava()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            } else {
                float f = 0.91F;
                if (this.isOnGround()) {
                    f = this.getWorld().getBlockState(this.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91F;
                }

                float g = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (this.isOnGround()) {
                    f = this.getWorld().getBlockState(this.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91F;
                }

                this.updateVelocity(this.isOnGround() ? 0.1F * g : 0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(f));
            }
        }

        this.updateLimbs(false);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        //do nothing
    }

    @Override
    public void applyLeashElasticity(Entity leashHolder, float distance) {
        super.applyLeashElasticity(leashHolder, distance + 1.0f);
    }

    @Override
    public Vec3d getLeashPos(float delta) {
        return super.getLeashPos(delta * 0.3f);
    }

    public void setShooting(boolean shooting) {
        this.getDataTracker().set(SHOOTING, shooting);
    }

    public boolean isAngry() {
        return this.getDataTracker().get(SHOOTING);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return false;
    }

    private double getPassengerAttachmentY(boolean primaryPassenger, EntityDimensions dimensions, float scaleFactor) {
        double d = dimensions.height() - 0.375F * scaleFactor;
        float f = scaleFactor * 1.43F;
        float g = f - scaleFactor * 0.2F;
        float h = f - g;
        boolean bl2 = this.isSitting();
            int i = bl2 ? 40 : 52;
            int j;
            float k;
            if (bl2) {
                j = 28;
                k = primaryPassenger ? 0.5F : 0.1F;
            } else {
                j = primaryPassenger ? 24 : 32;
                k = primaryPassenger ? 0.6F : 0.35F;
            }

            float l = MathHelper.clamp((float)this.getWorld().getTime() + (float) 0.0, 0.0F, (float)i);
            boolean bl3 = l < (float)j;
            float m = bl3 ? l / (float)j : (l - (float)j) / (float)(i - j);
            float n = f - k * g;
            d += bl2 ? (double)MathHelper.lerp(m, bl3 ? f : n, bl3 ? n : h) : (double)MathHelper.lerp(m, bl3 ? h - f : h - n, bl3 ? h - n : 0.0F);


        if (bl2) {
            d += h;
        }

        return d;
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        int i = Math.max(this.getPassengerList().indexOf(passenger), 0);
        boolean bl = i == 0;
        float f = 0.5F;
        float g = (float)(this.isRemoved() ? 0.01F : this.getPassengerAttachmentY(bl, dimensions, scaleFactor));
        float h = 0.0F;
        if (this.getPassengerList().size() > 1) {
            if (!bl) {
                if (i == 1) {
                    h -= 0.665f;
                }
                if (i >= 2) {
                    f = -0.7F;
                    h += i == 3 ? 0.665f : -0.665f;
                }
            } else {
                h += 0.665f;
            }

            if (passenger instanceof AnimalEntity) {
                f += 0.2F;
            }
        }
        return new Vec3d(h, g + 0.3725f, f * scaleFactor + this.getPassengerList().size() < 2 ? 1.3f : 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isOf(Items.LEAD) || this.getLeashHolder() == player) {
            return super.interactMob(player, hand);
        }
        if (player.getStackInHand(hand).isEmpty() && !player.isSneaking()) {
            if (this.isTamed() && !this.isBaby() && this.getPassengerList().size() < 4 && this.hasHarness()) {
                this.playSound(ModSoundEvents.HAPPY_GHAST_GOGGLES_DOWN);
                player.startRiding(this);
            }
            return ActionResult.SUCCESS;
        }
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isTamed()) {
            if (this.isOwner(player)) {
                if (this.hasHarness() && itemStack.isOf(Items.SHEARS)) {
                    this.playSound(ModSoundEvents.HAPPY_GHAST_HARNESS_OFF);
                    this.dataTracker.set(HARNESS_COLOR, NO_HARNESS);
                    this.setFestive(false);
                    this.setLeashData(null);
                    return ActionResult.SUCCESS;
                }
                if (itemStack.getItem() instanceof HarnessItem harnessItem) {
                    this.playSound(ModSoundEvents.HAPPY_GHAST_HARNESS_ON);
                    this.setHarnessColor(harnessItem.getDyeColor());
                    if (harnessItem.isForFestival()) this.setFestive(true);
                    itemStack.decrementUnlessCreative(1, player);
                    return ActionResult.SUCCESS;
                }
                if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.getWorld().isClient()) {
                        this.playSound(SoundEvents.BLOCK_SNOW_BREAK);
                        this.eat(player, hand, itemStack);
                        FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                        this.heal(foodComponent != null ? (float)foodComponent.nutrition() : 1.0F);
                    }

                    return ActionResult.success(this.getWorld().isClient());
                }

                ActionResult actionResult = super.interactMob(player, hand);
                if (!actionResult.isAccepted()) {
                    this.setSitting(!this.isSitting());
                    this.playSound(ModSoundEvents.HAPPY_GHAST_GOGGLES_UP);
                    return ActionResult.success(this.getWorld().isClient());
                }

                return actionResult;
            }
        } else if (this.isBreedingItem(itemStack)) {
            if (!this.getWorld().isClient()) {
                this.eat(player, hand, itemStack);
                this.playSound(SoundEvents.BLOCK_SNOW_BREAK);
                this.tryTame(player);
                this.setPersistent();
            }

            return ActionResult.success(this.getWorld().isClient());
        }

        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            this.playSound(ModSoundEvents.HAPPY_GHAST_GOGGLES_UP);
            this.setPersistent();
        }

        return actionResult;
    }

    private void tryTame(PlayerEntity player) {
        if (this.random.nextInt(3) == 0) {
            this.setOwner(player);
            this.setSitting(true);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
        } else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTamed() && this.age > 4200;
    }

    @Override
    public void tick() {
        if (this.isTamed() && this.hasHarness()) {
            this.setAiDisabled(true);
        }
        if (this.getWorld().isRaining() || this.getY() > 220) {
            if (this.age % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(1.0F);
            }
        } else {
            if (this.age % 400 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(1.0F);
            }
        }
        super.tick();
    }

    @Override
    public int getAngerTime() {
        return this.getDataTracker().get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.getDataTracker().set(ANGER_TIME, angerTime);
    }

    @Override
    public @Nullable UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    static class ShootSnowBallGoal extends Goal {

        private final HappyGhastEntity ghast;
        public int cooldown;

        public ShootSnowBallGoal(HappyGhastEntity ghast) {
            this.ghast = ghast;
        }

        @Override
        public boolean canStart() {
            return this.ghast.getTarget() != null && !this.ghast.isTamed() && this.ghast.getHealth() < this.ghast.getMaxHealth() && !this.ghast.isBaby();
        }

        @Override
        public void stop() {
            this.ghast.setShooting(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.ghast.getTarget();
            if (livingEntity != null) {
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0 && this.ghast.canSee(livingEntity)) {
                    World world = this.ghast.getWorld();
                    this.cooldown++;
                    if (this.cooldown == 10 && !this.ghast.isSilent()) {
                        world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.ghast.getBlockPos(), 0);
                    }

                    if (this.cooldown == 20) {
                        Vec3d vec3d = this.ghast.getRotationVec(1.0F);
                        double f = livingEntity.getX() - (this.ghast.getX() + vec3d.x * 4.0);
                        double g = livingEntity.getBodyY(0.5) - (0.5 + this.ghast.getBodyY(0.5));
                        double h = livingEntity.getZ() - (this.ghast.getZ() + vec3d.z * 4.0);
                        if (!this.ghast.isSilent()) {
                            world.syncWorldEvent(null, WorldEvents.GHAST_SHOOTS, this.ghast.getBlockPos(), 0);
                        }

                        SnowballEntity snowballEntity = new SnowballEntity(world, f, g, h);
                        snowballEntity.setPosition(this.ghast.getX() + vec3d.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, snowballEntity.getZ() + vec3d.z * 4.0);
                        snowballEntity.setVelocity(f, g, h, 1.6F, 12.0F);
                        world.spawnEntity(snowballEntity);
                        this.cooldown = -40;
                    }
                } else if (this.cooldown > 0) {
                    this.cooldown--;
                }

                this.ghast.setShooting(this.cooldown > 10);
            }
        }
    }

    static class FlyRandomlyGoal extends Goal {
        private final HappyGhastEntity ghast;

        public FlyRandomlyGoal(HappyGhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.ghast.getX();
                double e = moveControl.getTargetY() - this.ghast.getY();
                double f = moveControl.getTargetZ() - this.ghast.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.ghast.getRandom();
            double d = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double f = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    static class LookAtTargetGoal extends Goal {
        private final HappyGhastEntity ghast;

        public LookAtTargetGoal(HappyGhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ghast.hasPassengers() && this.ghast.isTamed() && this.ghast.hasHarness()) {
                this.ghast.playSound(ModSoundEvents.HAPPY_GHAST_RIDING);
            }
            if (this.ghast.getTarget() == null) {
                Vec3d vec3d = this.ghast.getVelocity();
                this.ghast.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * (180.0F / (float)Math.PI));
                this.ghast.bodyYaw = this.ghast.getYaw();
            } else {
                LivingEntity livingEntity = this.ghast.getTarget();
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0) {
                    double e = livingEntity.getX() - this.ghast.getX();
                    double f = livingEntity.getZ() - this.ghast.getZ();
                    this.ghast.setYaw(-((float)MathHelper.atan2(e, f)) * (180.0F / (float)Math.PI));
                    this.ghast.bodyYaw = this.ghast.getYaw();
                }
            }
        }
    }

    @Override
    public boolean isCollidable() {
        return !this.isBaby() && this.hasHarness();
    }

    static class GhastMoveControl extends MoveControl {
        private final HappyGhastEntity ghast;
        private int collisionCheckCooldown;

        public GhastMoveControl(HappyGhastEntity ghast) {
            super(ghast);
            this.ghast = ghast;
        }

        @Override
        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown = this.collisionCheckCooldown + this.ghast.getRandom().nextInt(5) + 2;
                    Vec3d vec3d = new Vec3d(this.targetX - this.ghast.getX(), this.targetY - this.ghast.getY(), this.targetZ - this.ghast.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.willCollide(vec3d, MathHelper.ceil(d))) {
                        this.ghast.setVelocity(this.ghast.getVelocity().add(vec3d.multiply(0.1)));
                    } else {
                        this.state = MoveControl.State.WAIT;
                    }
                }
            }
        }

        private boolean willCollide(Vec3d direction, int steps) {
            Box box = this.ghast.getBoundingBox();

            for (int i = 1; i < steps; i++) {
                box = box.offset(direction);
                if (!this.ghast.getWorld().isSpaceEmpty(this.ghast, box)) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSoundEvents.HAPPY_GHAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.HAPPY_GHAST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.HAPPY_GHAST_DEATH;
    }
}

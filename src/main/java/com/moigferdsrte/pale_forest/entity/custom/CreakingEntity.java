package com.moigferdsrte.pale_forest.entity.custom;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.entity.ai.CreakAttackGoal;
import com.moigferdsrte.pale_forest.sound.ModSoundEvents;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CreakingEntity extends HostileEntity {

    private static final TrackedData<Boolean> IS_ATTACKING =
            DataTracker.registerData(CreakingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState invulnerabilityAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    public static final AnimationState idleAnimation = new AnimationState();

    private int attackAnimationRemainingTicks;
    private int invulnerabilityAnimationRemainingTicks;


    private boolean isMoving = true;

    public int idleAnimationTimeOut = 0;

    public static final AnimationState attackAnimation = new AnimationState();

    public int attackAnimationTimeOut = 0;

    public CreakingEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    private void setIdleAnimation(){
        if (idleAnimationTimeOut <= 0){
            idleAnimationTimeOut = this.random.nextInt( 40) + 80;
            idleAnimation.start(this.age);
        }else {
            idleAnimationTimeOut--;
        }

        if (this.isAttacking() && attackAnimationTimeOut <= 0) {
            attackAnimationTimeOut = 40;
            attackAnimation.start(this.age);
        } else {
            attackAnimationTimeOut--;
        }

        if (!this.isAttacking()) {
            attackAnimation.stop();
        }
    }

    private void setupAnimationStates() {
        this.attackAnimationState.setRunning(this.attackAnimationRemainingTicks > 0, this.age);
        this.invulnerabilityAnimationState.setRunning(this.invulnerabilityAnimationRemainingTicks > 0, this.age);
        this.deathAnimationState.setRunning(this.isAttacking(), this.age);
    }

    @Override
    public void tick() {
        //super.tick();
        setIdleAnimation();

        PlayerEntity closestPlayer = this.getWorld().getClosestPlayer(this, 10.0D);

        if (this.getSteppingBlockState().isOf(ModBlocks.CREAKING_HEART)){
            this.setInvulnerable(true);
        }

        isMoving = closestPlayer == null || !isPlayerStaring(closestPlayer);
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
        if (isMoving) {
            super.tick();
        }else {
            this.setMovementSpeed(0f);
        }
    }

    @Override
    public void tickMovement() {
        PlayerEntity closestPlayer = this.getWorld().getClosestPlayer(this, 10.0D);

        if (this.getWorld().isClient) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.getWorld()
                        .playSound(
                                this.getX() + 0.5,
                                this.getY() + 0.5,
                                this.getZ() + 0.5,
                                ModSoundEvents.CREAKING_CREAK,
                                this.getSoundCategory(),
                                1.0F + this.random.nextFloat(),
                                this.random.nextFloat() * 0.7F + 0.3F,
                                false
                        );
            }
            for (int i = 0; i < 2; i++) {
                this.getWorld().addParticle(ParticleTypes.WHITE_ASH, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
            }
        }

        isMoving = closestPlayer == null || !isPlayerStaring(closestPlayer);

        if (isMoving) {
            super.tickMovement();
        }else {
            this.setMovementSpeed(0f);
        }
    }


    @Override
    protected void checkBlockCollision() {
        super.checkBlockCollision();
    }

    boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack = player.getInventory().armor.get(3);
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            return e > 1.0 - 0.025 / d && player.canSee(this);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.GLOWSTONE_DUST), false));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.goalSelector.add(2, new CreakAttackGoal(this, 1.0D, true));
        this.goalSelector.add(1, new AttackGoal(this));
    }

    public static DefaultAttributeContainer.Builder createCreakingAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 18);
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.CREAKING_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.CREAKING_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.CREAKING_DEATH;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_ATTACKING, false);
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(IS_ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(IS_ATTACKING);
    }


}

package com.moigferdsrte.pale_forest.entity.ai;

import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

public class CreakAttackGoal extends MeleeAttackGoal {
    private final CreakingEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;
    public CreakAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.entity = (CreakingEntity) mob;
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 20;
        ticksUntilNextAttack = 20;
    }

    @Override
    public void stop() {
        super.stop();
        entity.setAttacking(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack -1, 0);
        }
    }

    @Override
    protected void attack(LivingEntity target) {
        if (isEnemyWithinDistance(target)) {
            shouldCountTillNextAttack = true;
            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }
            if (isTimeToAttack()) {
                this.mob.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
                performAttack(target);
            }
        } else {
            resetAttackCooldown();
            this.shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeOut = 0;
        }
    }

    private void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    private void performAttack(LivingEntity target) {
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(target);
    }

    private boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    private boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= this.attackDelay;
    }

    private boolean isEnemyWithinDistance(LivingEntity target) {
        return this.entity.distanceTo(target) < 2.0f;
    }
}

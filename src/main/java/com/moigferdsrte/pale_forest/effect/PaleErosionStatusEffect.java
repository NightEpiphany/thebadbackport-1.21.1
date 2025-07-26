package com.moigferdsrte.pale_forest.effect;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.sound.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class PaleErosionStatusEffect extends StatusEffect {
    private static final Identifier HEALTH_MODIFIER_ID = Identifier.of(ThePaleForest.MOD_ID, "pale_erosion");

    public PaleErosionStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x433b39);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.playSound(ModSoundEvents.CREAKING_CREAK, 1.0F, 1.0F);
            double reduction = -2.0 * (amplifier + 1);
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
                    .addPersistentModifier(new EntityAttributeModifier(
                            HEALTH_MODIFIER_ID,
                            reduction,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ));
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(HEALTH_MODIFIER_ID);
    }
}

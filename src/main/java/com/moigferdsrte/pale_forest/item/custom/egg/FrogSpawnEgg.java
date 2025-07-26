package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class FrogSpawnEgg extends GeneralCustomSpawnEgg{

    private final FrogIdentifier variant;

    public FrogSpawnEgg(FrogIdentifier variant, Settings settings) {
        super(EntityType.FROG, settings);
        this.variant = variant;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof FrogEntity frog) {
            Registries.FROG_VARIANT.getEntry(variant.getVariantKey())
                    .ifPresent(frog::setVariant);
        }
    }

    public enum FrogIdentifier {

        TEMPERATE("temperate"),
        WARM("warm"),
        COLD("cold"),;

        private final String name;

        private final RegistryKey<FrogVariant> variantKey;

        private FrogIdentifier(String variantString) {
            Identifier variant = Identifier.ofVanilla(variantString);
            this.name = variantString;
            this.variantKey = RegistryKey.of(RegistryKeys.FROG_VARIANT, variant);
        }

        public RegistryKey<FrogVariant> getVariantKey() {
            return variantKey;
        }

        public String getName() {
            return name;
        }
    }
}

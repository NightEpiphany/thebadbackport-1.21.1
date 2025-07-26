package com.moigferdsrte.pale_forest.item.custom.egg;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.CatVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class CatSpawnEgg extends GeneralCustomSpawnEgg{

    private final CatIdentifiers variant;

    public CatSpawnEgg(CatIdentifiers variant, Settings settings) {
        super(EntityType.CAT, settings);
        this.variant = variant;
    }

    @Override
    public void operation(MobEntity entity) {
        if (entity instanceof CatEntity cat) {
            Registries.CAT_VARIANT.getEntry(variant.getVariantKey())
                    .ifPresent(cat::setVariant);
        }
    }

    public enum CatIdentifiers {

        TABBY("tabby"),
        BLACK("black"),
        RED("red"),
        SIAMESE("siamese"),
        BRITISH_SHORTHAIR("british_shorthair"),
        CALICO("calico"),
        PERSIAN("persian"),
        RAGDOLL("ragdoll"),
        WHITE("white"),
        JELLIE("jellie"),
        ALL_BLACK("all_black");

        private final RegistryKey<CatVariant> variantKey;

        private final String name;

        CatIdentifiers(String variantString)  {
            Identifier variant = Identifier.ofVanilla(variantString);
            this.name = variantString;
            this.variantKey = RegistryKey.of(RegistryKeys.CAT_VARIANT, variant);
        }

        public RegistryKey<CatVariant> getVariantKey() {
            return variantKey;
        }

        public String getName() {
            return name;
        }
    }
}

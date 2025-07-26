package com.moigferdsrte.pale_forest.item;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModPaintings {

    public static final RegistryKey<PaintingVariant> DENNIS = of("dennis");


    private static RegistryKey<PaintingVariant> of(String id) {
        return RegistryKey.of(RegistryKeys.PAINTING_VARIANT, Identifier.of(ThePaleForest.MOD_ID, id));
    }

    public static void init() {}
}

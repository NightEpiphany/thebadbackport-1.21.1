package com.moigferdsrte.pale_forest.item;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ModArmorTrimMaterials {

    public static final RegistryKey<ArmorTrimMaterial> RESIN = of("resin");

    public static void bootstrap(Registerable<ArmorTrimMaterial> registry) {
        register(registry, RESIN, ModItems.RESIN_BRICK, Style.EMPTY.withColor(16545810), 0.5F);
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style, float itemModelIndex) {
        register(registry, key, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(
            Registerable<ArmorTrimMaterial> registry,
            RegistryKey<ArmorTrimMaterial> key,
            Item ingredient,
            Style style,
            float itemModelIndex,
            Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials
    ) {
        ArmorTrimMaterial armorTrimMaterial = ArmorTrimMaterial.of(
                key.getValue().getPath(),
                ingredient,
                itemModelIndex,
                Text.translatable(Util.createTranslationKey("trim_material", key.getValue())).fillStyle(style),
                overrideArmorMaterials
        );
        registry.register(key, armorTrimMaterial);
    }

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(ThePaleForest.MOD_ID, id));
    }
}

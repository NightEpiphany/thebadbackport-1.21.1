package com.moigferdsrte.pale_forest.mixin.extra.access;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OverworldBiomeCreator.class)
public class OverworldBiomesAccessor {

    @Invoker
    public static void callAddBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {}
}

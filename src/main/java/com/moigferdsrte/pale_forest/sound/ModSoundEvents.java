package com.moigferdsrte.pale_forest.sound;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

    public static final SoundEvent CREAKING_HURT = register("creaking_hurt");

    public static final SoundEvent CREAKING_AMBIENT = register("creaking_ambient");

    public static final SoundEvent CREAKING_DEATH = register("creaking_death");

    public static final SoundEvent CREAKING_CREAK = register("creaking_creak");

    public static final SoundEvent CREAKING_HEART_BREAK = register("creaking_heart_break");

    public static final SoundEvent CREAKING_HEART_FALL = register("creaking_heart_fall");

    public static final SoundEvent CREAKING_HEART_STEP = register("creaking_heart_step");

    public static final SoundEvent CREAKING_HEART_HIT = register("creaking_heart_hit");

    public static final SoundEvent CREAKING_HEART_PLACE = register("creaking_heart_place");

    public static final SoundEvent HAPPY_GHAST_AMBIENT = register("happy_ghast_ambient");

    public static final SoundEvent HAPPY_GHAST_HURT = register("happy_ghast_hurt");

    public static final SoundEvent HAPPY_GHAST_DEATH = register("happy_ghast_death");

    public static final SoundEvent HAPPY_GHAST_HARNESS_ON = register("happy_ghast_harness_on");

    public static final SoundEvent HAPPY_GHAST_HARNESS_OFF = register("happy_ghast_harness_off");

    public static final SoundEvent HAPPY_GHAST_GOGGLES_UP = register("happy_ghast_goggles_up");

    public static final SoundEvent HAPPY_GHAST_GOGGLES_DOWN = register("happy_ghast_goggles_down");

    public static final SoundEvent HAPPY_GHAST_RIDING = register("happy_ghast_riding");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DISC_TEARS = registerReference("music_disc.tears");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DISC_LAVA_CHICKEN = registerReference("music_disc.lava_chicken");

    public static final BlockSoundGroup CREAKING = new BlockSoundGroup(1.0f, 1.0f, CREAKING_HEART_BREAK, CREAKING_HEART_STEP, CREAKING_HEART_PLACE, CREAKING_HEART_HIT, CREAKING_HEART_FALL);

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of(ThePaleForest.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(String name){
        Identifier id = Identifier.of(ThePaleForest.MOD_ID, name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSoundEvents(){

    }
}

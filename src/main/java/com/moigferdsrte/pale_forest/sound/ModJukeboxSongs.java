package com.moigferdsrte.pale_forest.sound;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public interface ModJukeboxSongs {

    RegistryKey<JukeboxSong> TEARS = of("tears");

    RegistryKey<JukeboxSong> LAVA_CHICKEN = of("lava_chicken");


    private static RegistryKey<JukeboxSong> of(String id) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(ThePaleForest.MOD_ID, id));
    }

    private static void register(
            Registerable<JukeboxSong> registry, RegistryKey<JukeboxSong> key, RegistryEntry.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput
    ) {
        registry.register(
                key, new JukeboxSong(soundEvent, Text.translatable(Util.createTranslationKey("jukebox_song", key.getValue())), (float)lengthInSeconds, comparatorOutput)
        );
    }

    static void bootstrap(Registerable<JukeboxSong> registry) {
        register(registry, TEARS, ModSoundEvents.MUSIC_DISC_TEARS, 176, 10);
        register(registry, LAVA_CHICKEN, ModSoundEvents.MUSIC_DISC_LAVA_CHICKEN, 135, 4);
    }
}

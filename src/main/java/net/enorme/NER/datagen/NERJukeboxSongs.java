package net.enorme.NER.datagen;

import net.enorme.NER.sound.NERSound;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.JukeboxSong;

public class NERJukeboxSongs {

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        context.register(NERSound.GENETIC_MIRACLE_KEY, new JukeboxSong(
                BuiltInRegistries.SOUND_EVENT.wrapAsHolder(NERSound.GENETIC_MIRACLE.get()),
                Component.translatable("item.nethers_exorcism_reborn.genetic_miracle_music_disc.desc"),
                192.6F,
                15));
    }
}

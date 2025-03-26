package com.github.lil_minion.couples.fabric.registry;

import com.github.lil_minion.Couples;
import com.github.lil_minion.uploaded.UploadedRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundsFabric {

    public static final SoundEvent KISS =  register("kiss");

    private static SoundEvent register(String id) {
        ResourceLocation soundLocation = new ResourceLocation(Couples.MOD_ID, id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT,soundLocation,
                SoundEvent.createVariableRangeEvent(soundLocation));
    }

    public static void register() {}

}

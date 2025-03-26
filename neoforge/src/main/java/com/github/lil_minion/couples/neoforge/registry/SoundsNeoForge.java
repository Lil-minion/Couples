package com.github.lil_minion.couples.neoforge.registry;

import com.github.lil_minion.Couples;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundsNeoForge {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Couples.MOD_ID);

    // All vanilla sounds use variable range events.
    public static final DeferredHolder<SoundEvent, SoundEvent> KISS = SOUND_EVENTS.register(
            "kiss",
            () -> SoundEvent.createVariableRangeEvent(
                    new ResourceLocation(Couples.MOD_ID, "kiss"))
    );

    /**
     * Registers the sounds with the event bus.
     *
     * @param eventBus The event bus to register the sounds with.
     */
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}

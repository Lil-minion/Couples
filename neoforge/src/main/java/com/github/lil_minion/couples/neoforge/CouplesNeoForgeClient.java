package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.Couples;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Couples.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CouplesNeoForgeClient {

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {/* Empty until needed */}

}

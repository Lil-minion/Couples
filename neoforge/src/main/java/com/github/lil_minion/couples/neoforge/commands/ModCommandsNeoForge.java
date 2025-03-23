package com.github.lil_minion.couples.neoforge.commands;

import com.github.lil_minion.Couples;
import com.github.lil_minion.command.CoupleCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Couples.MOD_ID)
public class ModCommandsNeoForge {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CoupleCommands.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

}

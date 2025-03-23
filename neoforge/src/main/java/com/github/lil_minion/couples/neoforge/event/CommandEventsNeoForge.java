package com.github.lil_minion.couples.neoforge.event;

import com.github.lil_minion.Couples;
import com.github.lil_minion.command.CoupleCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Couples.MOD_ID)
public class CommandEventsNeoForge {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CoupleCommand.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

}

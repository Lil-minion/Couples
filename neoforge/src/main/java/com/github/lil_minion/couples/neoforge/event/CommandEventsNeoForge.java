package com.github.lil_minion.couples.neoforge.event;

import com.github.lil_minion.Couples;
import com.github.lil_minion.command.CoupleCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * This class handles command registration events for the Couples mod using NeoForge.
 * It listens for command registration events and registers the custom commands defined in the mod.
 */
@EventBusSubscriber(modid = Couples.MOD_ID)
public class CommandEventsNeoForge {

    /**
     * Registers custom commands when the RegisterCommandsEvent is fired.
     *
     * @param event The event containing information about the command registration process.
     */
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CoupleCommand.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

}

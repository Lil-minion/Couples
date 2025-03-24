package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.command.CoupleCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/**
 * Handles the registration of commands for the mod.
 */
public class CommandEventsFabric {

    /**
     * Registers the commands for the mod.
     */
    public static void register() {
        CommandRegistrationCallback.EVENT.register(CoupleCommand::register);
    }

}

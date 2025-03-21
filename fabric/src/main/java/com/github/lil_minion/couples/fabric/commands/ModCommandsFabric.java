package com.github.lil_minion.couples.fabric.commands;

import com.github.lil_minion.command.CoupleCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommandsFabric {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(CoupleCommands::register);
    }

}

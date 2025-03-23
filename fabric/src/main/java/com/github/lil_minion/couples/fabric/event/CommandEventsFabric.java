package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.command.CoupleCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandEventsFabric {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(CoupleCommand::register);
    }

}

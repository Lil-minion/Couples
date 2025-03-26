package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.MailScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/**
 * Handles simple text commands
 */
public class CommandHandler {


    public static void handle(String command, Player player) {
        switch (command) {
            case "gui.inbox" -> openMailScreen();
        }
    }

    /**
     * Handles the request to open the inbox screen for the specified player.
     */
    public static void openMailScreen() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(new MailScreen());
    }
}

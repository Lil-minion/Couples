package com.github.lil_minion.client.screen;

import com.github.lil_minion.client.data.InboxVolatileData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Represents the mail screen in the client interface.
 * This screen displays the user's inbox and allows interaction with mail items.
 */
public class MailScreen extends Screen {

    /**
     * Constructs a new MailScreen with the specified inbox.
     */
    public MailScreen() {
        super(Component.translatable("gui.screen.couples.mail_screen"));

        InboxVolatileData.getClientInbox().getMails().forEach(System.out::println);
    }

}

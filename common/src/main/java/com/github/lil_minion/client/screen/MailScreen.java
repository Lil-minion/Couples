package com.github.lil_minion.client.screen;

import com.github.lil_minion.model.mail.Inbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Represents the mail screen in the client interface.
 * This screen displays the user's inbox and allows interaction with mail items.
 */
public class MailScreen extends Screen {

    final Inbox inbox;

    /**
     * Constructs a new MailScreen with the specified inbox.
     *
     * @param inbox The inbox to be displayed on this screen.
     */
    public MailScreen(Inbox inbox) {
        super(Component.translatable("gui.screen.couples.mail_screen"));
        this.inbox = inbox;
    }

}

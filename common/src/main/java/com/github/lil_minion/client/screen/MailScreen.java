package com.github.lil_minion.client.screen;

import com.github.lil_minion.model.mail.Inbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MailScreen extends Screen {

    final Inbox inbox;

    public MailScreen(Inbox inbox) {
        super(Component.translatable("gui.screen.couples.mail_screen"));
        this.inbox = inbox;
    }

}

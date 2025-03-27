package com.github.lil_minion.client.screen;

import com.github.lil_minion.client.data.InboxVolatileData;
import com.github.lil_minion.client.screen.widget.InboxWidget;
import com.github.lil_minion.model.mail.Inbox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Represents the mail screen in the client interface.
 * This screen displays the user's inbox and allows interaction with mail items.
 */
public class MailScreen extends Screen {

    final Inbox inbox = InboxVolatileData.getClientInbox();

    /**
     * Constructs a new MailScreen with the specified inbox.
     */
    public MailScreen() {
        super(Component.translatable("gui.screen.couples.mail_screen"));
    }

    @Override
    public void init() {
        super.init();

        // Define the position and size of the InboxWidget
        int x = 10; // X position
        int y = 10; // Y position
        int entryHeight = 30; // Height of each email entry
        InboxWidget inboxWidget = new InboxWidget(x, y, width - 30, height - 60 , entryHeight, inbox, this);

        Button closeButton = Button.builder(
                        Component.translatable("gui.done"), sender -> close())
                .bounds(width / 2 - 100, height - 45, 200, 20)
                .build();

        addRenderableWidget(inboxWidget);
        addRenderableWidget(closeButton);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderTransparentBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);

        if (inbox == null || inbox.getMails().isEmpty()) {
            graphics.drawCenteredString(minecraft.font, Component.translatable("gui.screen.couples.no_mail"),
                    width / 2, height / 2, 0xFFFFFFFF);
        }

    }

    public void close() {
        minecraft.setScreen(null);
    }

}

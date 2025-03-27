package com.github.lil_minion.client.screen.widget;

import com.github.lil_minion.client.screen.CustomBookViewScreen;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class InboxWidget extends AbstractScrollWidget {

    private static final int MIN_BRIGHTNESS = 90; // Minimum brightness threshold
    private final Screen parent;
    private final int entryHeight;
    private final Inbox inbox;
    private final Minecraft minecraft = Minecraft.getInstance();
    private final List<Mail> mails;
    private int scrollAmount = 0;

    public InboxWidget(int x, int y, int width, int height, int entryHeight, Inbox inbox, Screen parent) {
        super(x, y, width, height, Component.empty());
        super.height = Math.min(super.height, (inbox.getMails().size() * entryHeight) + 10);
        this.entryHeight = entryHeight;
        this.inbox = inbox;
        this.parent = parent;
        mails = inbox.getMails().reversed();
    }

    public static int getColorFromString(String input) {
        try {
            // Create an SHA-256 hash of the input string
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes());

            int r = hash[0] & 0xFF; // Red component
            int g = hash[1] & 0xFF; // Green component
            int b = hash[2] & 0xFF;
            // Adjust brightness if necessary
            int brightness = (int) (0.299 * r + 0.587 * g + 0.114 * b);
            if (brightness < MIN_BRIGHTNESS) {
                // Increase brightness by scaling the RGB values
                float scale = (float) MIN_BRIGHTNESS / brightness;
                r = Math.min(255, (int) (r * scale));
                g = Math.min(255, (int) (g * scale));
                b = Math.min(255, (int) (b * scale));
            }
            return (r << 16) | (g << 8) | b; // 0xRRGGBB format
            // Combine RGB components into a single int value
        } catch (NoSuchAlgorithmException e) {
            return 0xcc53ce;
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    @Override
    protected int getInnerHeight() {
        return mails.size() * entryHeight;
    }

    @Override
    protected double scrollRate() {
        return 3.0;
    }

    @Override
    protected void renderBackground(GuiGraphics guiGraphics) {
        // TODO replace with proper menu texture
        int backgroundColor = 0x80818080; // ARGB format: Alpha (FF) Red (00) Green (00) Blue (FF)
        guiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor);
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int borderColor = 0xFFFFFFFF;
        int textColor = 0xFFFFFFFF;
        for (int i = 0; i < mails.size(); i++) {
            Mail mail = mails.get(i);
            int yPosition = 10 + (i * entryHeight);

            guiGraphics.fill(30, getY() + yPosition + entryHeight - 5, getWidth(),
                    getY() + yPosition + entryHeight - 4, borderColor);
            guiGraphics.drawString(minecraft.font, mail.senderName(), 30, getY() + yPosition, getColorFromString(mail.senderName()));
            guiGraphics.drawString(minecraft.font, mail.message().getFirst(), 30, getY() + yPosition + 10, textColor);
            guiGraphics.drawString(minecraft.font, (minecraft.level.getGameTime() - mail.timestamp()) / 24000 + " "
                            + Component.translatable("gui.inbox.days").getString(),
                    getX() + getWidth() - 70, getY() + yPosition, textColor);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            for (int i = 0; i < mails.size(); i++) {
                int yPosition = 10 + (i * entryHeight) - (int) scrollAmount();
                if (mouseY >= getY() + yPosition && mouseY < getY() + yPosition + entryHeight) {
                    openBookScreen(mails.get(i));
                    return true;
                }
            }
        }
        return false;
    }

    private void openBookScreen(Mail mail) {
        BookViewScreen.BookAccess access = new BookViewScreen.BookAccess(mail.message());
        CustomBookViewScreen bookViewScreen = new CustomBookViewScreen(access, parent);
        minecraft.setScreen(bookViewScreen);
    }

}

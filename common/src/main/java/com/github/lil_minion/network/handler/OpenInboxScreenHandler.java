package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.MailScreen;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import com.github.lil_minion.utils.InboxUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class OpenInboxScreenHandler {

    public static void handle(OpenInboxScreenMessage openInboxScreenMessage, Player player) {
        Inbox inbox = InboxUtil.decodeInbox(openInboxScreenMessage, player);

        Minecraft minecraft = Minecraft.getInstance();
        MailScreen mailScreen = new MailScreen(inbox);
        minecraft.setScreen(mailScreen);

    }

}

package com.github.lil_minion.network;

import com.github.lil_minion.ModLoaderMethods;
import com.github.lil_minion.server.data.Mail;
import com.github.lil_minion.utils.InboxUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MailHandler {

    public static void handle(MailMessage mailMessage, Player player) {

        Mail mail = InboxUtil.decodeMailMessage(mailMessage);

        if (player instanceof ServerPlayer serverPlayer) {
            // When mail is sent to server resend back to recipient player
            ModLoaderMethods.sendMessageToClientMethod.accept(serverPlayer, mailMessage);

        } else if (player instanceof LocalPlayer localPlayer) {
            //  When mail is received by player notify player
            localPlayer.displayClientMessage(Component.translatable("messages.couples.you_got_mail"), false);

            if (player.getServer().isDedicatedServer()) {
                InboxUtil.storeMail(mail);
            }
        }
    }

}

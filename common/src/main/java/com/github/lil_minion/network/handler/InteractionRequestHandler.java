package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.InteractionRequestScreen;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.utils.InteractionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Handles the processing of interaction request messages sent between players.
 * This class is responsible for opening request screens for target players
 */
public class InteractionRequestHandler {

    /**
     * Handles the received interaction request message for the specified player.
     *
     * @param requestMessage the interaction request message to be handled
     * @param player         the player who received the interaction request message
     */
    public static void handle(InteractionRequestMessage requestMessage, Player player) {

        InteractionRequest request = InteractionUtil.decodeInteractionRequestMessage(requestMessage);

        if (player instanceof LocalPlayer localPlayer) {
            InteractionRequestScreen screen = new InteractionRequestScreen(request, localPlayer);
            Minecraft.getInstance().setScreen(screen);
        } else if (request.accepted()) {
            // Todo implement server-side handling
            switch (request.interactionRequestType()) {
                //case MARRIAGE_PROPOSAL -> ;
                //case FLIRT -> ;
                //case KISS -> ;
            }
        }
    }
}

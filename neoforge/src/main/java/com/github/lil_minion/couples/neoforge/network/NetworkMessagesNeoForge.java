package com.github.lil_minion.couples.neoforge.network;

import com.github.lil_minion.network.handler.InteractionRequestHandler;
import com.github.lil_minion.network.handler.MailMessageHandler;
import com.github.lil_minion.network.handler.OpenInboxScreenHandler;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * Handles the registration of network messages for the mod.
 * This class is responsible for setting up networking, including
 * message handling and payload registration.
 */
public class NetworkMessagesNeoForge {

    /**
     * Registers payload types and handlers.
     *
     * @param event The event used to register the handlers.
     */
    public static void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(
                MailMessage.ID,
                MailMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (mailMessage, context) ->
                                MailMessageHandler.handle(mailMessage, context.player()),
                        (mailMessage, context) ->
                                MailMessageHandler.handle(mailMessage, context.player())
                )
        );

        registrar.playBidirectional(
                OpenInboxScreenMessage.ID,
                OpenInboxScreenMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (openInboxScreenMessage, context) ->
                                OpenInboxScreenHandler.handle(openInboxScreenMessage, context.player()),
                        (ignored1,ignored2) ->{}
                )
        );

        registrar.playBidirectional(
                InteractionRequestMessage.ID,
                InteractionRequestMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (requestMessage, context) ->
                                InteractionRequestHandler.handle(requestMessage, context.player()),
                        (requestMessage, context) ->
                                InteractionRequestHandler.handle(requestMessage, context.player())
                )
        );
    }

}

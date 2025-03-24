package com.github.lil_minion.model.relationship.interaction;

import net.minecraft.network.chat.Component;

import java.util.UUID;

/**
 * Represents a request for interaction between two players.
 *
 * @param sender                 The {@link UUID} of the player sending the request.
 * @param recipient              The {@link UUID} of the player receiving the request.
 * @param timeStamp              The timestamp indicating when the request was made.
 * @param interactionRequestType  The type of interaction being requested.
 * @param message                The message associated with the interaction request.
 * @param accepted               Indicates whether the interaction request has been accepted.
 */
public record InteractionRequest(
        UUID sender,
        UUID recipient,
        long timeStamp,
        InteractionRequestType interactionRequestType,
        Component message,
        Boolean accepted
) {}

package com.github.lil_minion.utils;

import com.github.lil_minion.ModLoaderMethods;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.model.relationship.interaction.InteractionRequestType;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.server.data.InteractionVolatileData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class InteractionUtil {


    /**
     * Creates a {@link InteractionRequestMessage} from a {@link InteractionRequest}.
     *
     * @param request The {@link InteractionRequest} object to convert.
     * @return A {@link InteractionRequestMessage} representing the provided {@link InteractionRequest}.
     */
    public static InteractionRequestMessage createMessage(InteractionRequest request) {
        return new InteractionRequestMessage(
                request.sender().toString(),
                request.recipient().toString(),
                request.timeStamp(),
                request.interactionRequestType().getType(),
                request.message().getString(),
                request.accepted());
    }

    public static InteractionRequest decodeInteractionRequestMessage(InteractionRequestMessage request) {
        return new InteractionRequest(
                UUID.fromString(request.sender()),
                UUID.fromString(request.recipient()),
                request.timestamp(),
                InteractionRequestType.fromString(request.interactionType()),
                Component.literal(request.message()),
                request.accepted()
        );
    }

    /**
     * Sends an interaction request message to the specified player.
     *
     * @param player  The {@link Player} to send the interaction request to.
     * @param request The {@link InteractionRequest} to send.
     */
    public static void sendInteractionRequest(Player player, InteractionRequest request) {
        InteractionRequestMessage message = createMessage(request);
        if (player instanceof ServerPlayer serverPlayer) {
            ModLoaderMethods.sendMessageToClient(serverPlayer, message);
        } else {
            ModLoaderMethods.sendMessageToServer(message);
        }
    }

    /**
     * Stores the given {@link InteractionRequest} in a map linked to recipient player
     *
     * @param request The {@link InteractionRequest} to store.
     */
    public static void storeInteractionRequest(InteractionRequest request) {
        InteractionVolatileData.INTERACTION_COOLDOWN_MAP.put(request.sender(), request.timeStamp());
    }

    public static void interact(Player playerSource, ServerPlayer playerTarget, Component message, InteractionRequestType type) {
        InteractionRequest request = new InteractionRequest(playerSource.getUUID(),
                playerTarget.getUUID(), playerSource.level().getGameTime(),
                type, message, false
        );

        if (playerTarget instanceof ServerPlayer serverPlayer) {
            InteractionUtil.sendInteractionRequest(serverPlayer, request);
            InteractionUtil.storeInteractionRequest(request);
        }
    }
}

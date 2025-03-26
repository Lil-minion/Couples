package com.github.lil_minion.utils.relationship;

import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.model.relationship.interaction.InteractionRequestType;
import com.github.lil_minion.server.data.InteractionVolatileData;
import com.github.lil_minion.utils.network.MessageSenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class InteractionUtil {

    public static void interact(UUID sender, ServerPlayer target, Component message, InteractionRequestType type) {
        InteractionRequest request = new InteractionRequest(sender,
                target.getUUID(), target.level().getGameTime(),
                type, message, false
        );

        if (target instanceof ServerPlayer serverPlayerTarget) {
            MessageSenderUtil.sendInteractionRequest(serverPlayerTarget, request);
            InteractionVolatileData.INTERACTION_COOLDOWN_MAP.put(request.sender(), request.timeStamp());
        }
    }

    public static void interact(Player sender, ServerPlayer target, Component message, InteractionRequestType type) {
        interact(sender.getUUID(), target,message, type);
    }

}

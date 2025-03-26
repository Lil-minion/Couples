package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.InteractionRequestScreen;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.utils.EffectUtil;
import com.github.lil_minion.utils.relationship.MarriageUtil;
import com.github.lil_minion.utils.relationship.RomanceUtil;
import com.github.lil_minion.utils.network.MessageDecoderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.FireworkExplosion;

import java.util.ArrayList;
import java.util.List;

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

        InteractionRequest request = MessageDecoderUtil.decode(requestMessage);

        if (player instanceof LocalPlayer localPlayer) {
            InteractionRequestScreen screen = new InteractionRequestScreen(request, localPlayer);
            Minecraft.getInstance().setScreen(screen);
        } else if (request.accepted()) {
            // Todo implement server-side handling
            switch (request.interactionRequestType()) {
                case MARRIAGE_PROPOSAL -> marriageAccepted(player, request);
                case FLIRT_REQUEST -> flirtAccepted();
                case KISS_REQUEST -> kissAccepted();
            }
        }
    }

    private static void marriageAccepted(Player playerTarget, InteractionRequest request) {
        List<Romance> list = RomanceSavedData.ROMANCE_MAP.get(playerTarget.getUUID());

        // Assume the romance exists as it was checked before sending
        Romance romance = RomanceSavedData.ROMANCE_MAP.getOrDefault(request.recipient(), new ArrayList<>())
                .stream().filter(r -> r.isPlayerInRelationship(request.sender()))
                .findFirst().get();

        // Create marriage and delete romance
        Marriage marriage = new Marriage(request.sender(),
                request.recipient(), playerTarget.level().getGameTime());
        marriage.setTimesKissed(romance.getTimesKissed());
        marriage.setTimesFlirted(romance.getTimesFlirted());
        MarriageUtil.updateMarriage(marriage);
        list.remove(romance);
        RomanceUtil.updateRomance(romance);

        // Special effects on both players
        if (playerTarget instanceof ServerPlayer serverPlayerTarget) {
            Player playerSender = playerTarget.level().getPlayerByUUID(request.sender());
            if (playerSender instanceof ServerPlayer serverPlayerSender) {
                FireworkExplosion.Shape shape = FireworkExplosion.Shape.LARGE_BALL;
                int color = 0xdda0dd;
                for (ServerPlayer effectPlayer : List.of(serverPlayerSender, serverPlayerTarget)) {
                    EffectUtil.spawnParticlesNearby(effectPlayer, ParticleTypes.HEART, 50);
                    EffectUtil.spawnFireworksNearby(effectPlayer, shape, color, 1);
                    EffectUtil.spawnFireworksNearby(effectPlayer, shape, color, 2);
                    EffectUtil.spawnFireworksNearby(effectPlayer, shape, color, 2);
                    EffectUtil.spawnFireworksNearby(effectPlayer, shape, color, 3);
                }
            }
        }
    }

    private static void flirtAccepted() {

    }

    private static void kissAccepted() {

    }

}

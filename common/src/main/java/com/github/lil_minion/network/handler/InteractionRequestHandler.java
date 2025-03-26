package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.InteractionRequestScreen;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.utils.EffectUtil;
import com.github.lil_minion.utils.InteractionUtil;
import com.github.lil_minion.utils.MarriageUtil;
import com.github.lil_minion.utils.RomanceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.FireworkExplosion;

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

        InteractionRequest request = InteractionUtil.decodeInteractionRequestMessage(requestMessage);

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


    private static void marriageAccepted(Player player, InteractionRequest request) {
        List<Romance> list = RomanceSavedData.ROMANCE_MAP.get(player.getUUID());
        Romance previousRomance = null;

        for (Romance romance : list) {
            if (romance.isPlayerInRelationship(request.sender())) {
                previousRomance = romance;
                break;
            }
            if (previousRomance != null && previousRomance.getHearths() == 20) {
                Marriage marriage = new Marriage(request.sender(),
                        request.recipient(), player.level().getGameTime());
                marriage.setTimesKissed(previousRomance.getTimesKissed());
                marriage.setTimesFlirted(previousRomance.getTimesFlirted());
                MarriageUtil.updateMarriage(marriage);
                list.remove(previousRomance);
                RomanceUtil.updateRomance(previousRomance);
                if (player instanceof ServerPlayer serverPlayer) {
                    EffectUtil.spawnParticlesNearby(serverPlayer, ParticleTypes.HEART, 50);
                    EffectUtil.spawnFireworksNearby(serverPlayer, FireworkExplosion.Shape.LARGE_BALL, 0xdda0dd, 1);
                    EffectUtil.spawnFireworksNearby(serverPlayer, FireworkExplosion.Shape.LARGE_BALL, 0xdda0dd, 2);
                    EffectUtil.spawnFireworksNearby(serverPlayer, FireworkExplosion.Shape.LARGE_BALL, 0xdda0dd, 2);
                    EffectUtil.spawnFireworksNearby(serverPlayer, FireworkExplosion.Shape.LARGE_BALL, 0xdda0dd, 3);
                }
            }
        }
    }

    private static void flirtAccepted() {

    }

    private static void kissAccepted() {

    }
}

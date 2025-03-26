package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.event.handler.PlayerEventHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * This class is responsible for registering player-related event callback
 */
public class PlayerEventsFabric {

    /**
     * Registers the player event callbacks.
     */
    public static void register() {
        UseEntityCallback.EVENT.register(((player, level, interactionHand, entity, entityHitResult) -> {
            if (entity instanceof Player playerTarget) {
                PlayerEventHandler.onPlayerRightClick(player, playerTarget);
            }

            return InteractionResult.PASS;
        }));

        ClientEntityEvents.ENTITY_LOAD.register((Entity entity, ClientLevel level) -> {
            if (entity instanceof LocalPlayer player) {
                PlayerEventHandler.onPlayerJoinServer(player, level);
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerLevel level) -> {
            if (entity instanceof ServerPlayer player) {
                PlayerEventHandler.onPlayerJoinServer(player, level);
            }
        });
    }

}

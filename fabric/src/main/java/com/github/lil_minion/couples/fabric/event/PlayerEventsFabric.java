package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.event.handler.PlayerEventHandler;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class PlayerEventsFabric {

    public static void register() {
        UseEntityCallback.EVENT.register(((player, level, interactionHand, entity, entityHitResult) -> {
            if (entity instanceof Player playerTarget) {
                PlayerEventHandler.onPlayerRightClick(player, playerTarget);
            }

            return InteractionResult.PASS;
        }));
    }

}

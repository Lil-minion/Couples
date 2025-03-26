package com.github.lil_minion.couples.neoforge.event;

import com.github.lil_minion.Couples;
import com.github.lil_minion.event.handler.PlayerEventHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * This class handles player events for the Couples mod using NeoForge.
 */
@EventBusSubscriber(modid = Couples.MOD_ID)
public class PlayerEventsNeoForge {

    /**
     * Called when a player right-clicks on another entity.
     * If the target entity is a player, it invokes the appropriate handler method.
     *
     * @param event The event containing information about the player interaction.
     */
    @SubscribeEvent
    private static void onPlayerRightClick(PlayerInteractEvent.EntityInteract event) {
        Entity entityTarget = event.getTarget();

        if (entityTarget instanceof Player playerTarget) {
            Player playerOrigin = event.getEntity();
            PlayerEventHandler.onPlayerRightClick(playerOrigin, playerTarget);
        }
    }

    @SubscribeEvent
    private static void entityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerEventHandler.onPlayerJoinServer(player, event.getLevel());
        }
    }

    @SubscribeEvent
    private static void entityLeaveLevel(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerEventHandler.onPlayerLeaveServer(player, event.getLevel());
        }
    }


}

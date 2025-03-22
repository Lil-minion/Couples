package com.github.lil_minion.couples.neoforge.event;

import com.github.lil_minion.Couples;
import com.github.lil_minion.handler.PlayerEventHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Couples.MOD_ID)
public class PlayerEventsNeoForge {

    @SubscribeEvent
    private static void onPlayerRightClick(PlayerInteractEvent.EntityInteract event) {
        Entity entityTarget = event.getTarget();

        if (entityTarget instanceof Player playerTarget) {
            Player playerOrigin = event.getEntity();
            PlayerEventHandler.onPlayerRightClick(playerOrigin, playerTarget);
        }
    }

}

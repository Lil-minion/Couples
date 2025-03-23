package com.github.lil_minion.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class ChatUtil {

    public static Component createPlayerTranslatableComponent(Component playerName, String translatable) {
        MutableComponent message = Component.literal(playerName.toString() + " ");
        return message.append(Component.translatable(translatable));
    }

    public static Component createPlayerTranslatableComponent(Player player, String translatable) {
        return createPlayerTranslatableComponent(player.getName(), translatable);
    }

}

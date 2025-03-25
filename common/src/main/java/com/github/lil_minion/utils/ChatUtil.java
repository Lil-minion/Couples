package com.github.lil_minion.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

/**
 * Utility class for handling chat-related functionalities.
 */
public class ChatUtil {

    /**
     * Creates a translatable chat component that starts with the player's name and a space char.
     *
     * @param playerName   The {@link Component} representing the player's name.
     * @param translatable The key for the translatable component.
     * @return A {@link Component} that combines the player's name and the translatable message.
     */
    public static Component createPlayerTranslatableComponent(Component playerName, String translatable) {
        MutableComponent message = Component.literal(playerName.getString() + " ");
        return message.append(Component.translatable(translatable));
    }

    /**
     * Creates a translatable chat component that starts with the player's name and a space char.
     *
     * @param player       The {@link Player} whose name will be included in the message.
     * @param translatable The key for the translatable component.
     * @return A {@link Component} that combines the player's name and the translatable message.
     */
    public static Component createPlayerTranslatableComponent(Player player, String translatable) {
        return createPlayerTranslatableComponent(player.getName(), translatable);
    }

    /**
     * Creates a chat component that starts with the player's name and a space char.
     *
     * @param playerName The {@link Component} representing the player's name.
     * @param message    The {@link Component} to be appended after the player's name.
     * @return A {@link Component} that combines the player's name and the provided message.
     */
    public static Component createPlayerMessageComponent(Component playerName, Component message, String separator) {
        MutableComponent combinedMessage = Component.literal(playerName.getString() + separator);
        return combinedMessage.append(message);
    }

    /**
     * Creates a chat component that starts with the player's name and a space char.
     *
     * @param player The {@link Player} whose name will be included in the message.
     * @param message The {@link Component} to be appended after the player's name.
     * @return A {@link Component} that combines the player's name and the provided message.
     */
    public static Component createPlayerMessageComponent(Player player, Component message, String separator) {
        return createPlayerMessageComponent(player.getName(), message, separator);
    }

}

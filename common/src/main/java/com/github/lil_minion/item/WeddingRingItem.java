package com.github.lil_minion.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a wedding ring item that players can use to propose marriage to other players.
 * This item must be held in the player's main hand and is used to initiate marriage proposals.
 * When the target player accepts the proposal, the wedding ring is used to access the marriage menu.
 */
public class WeddingRingItem extends Item {

    /**
     * Constructs a new WeddingRingItem with the specified properties.
     *
     * @param properties The properties of the item.
     */
    public WeddingRingItem(Properties properties) {
        super(properties);
    }

    /**
     * Called when the item is used by a player.
     *
     * @param level    The level in which the item is being used.
     * @param player   The player using the item.
     * @param usedHand The hand in which the item is being used (main or offhand).
     * @return An {@link InteractionResultHolder} containing the result of the interaction and the item stack.
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }

}

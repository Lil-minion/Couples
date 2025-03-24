package com.github.lil_minion.model.mail;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Represents a mail message with details about the sender, recipient, type, timestamp, and content.
 *
 * @param sender    The UUID of the sender of the mail.
 *                  <p> This field can be null if system-generated.
 * @param recipient The UUID of the recipient of the mail.
 * @param type      The type of mail {@link MailType}.
 * @param timestamp The timestamp indicating when the mail was sent.
 * @param message   A list of {@link Component} objects representing the content of the mail message.
 */
public record Mail(@Nullable UUID sender,
                   @NotNull UUID recipient,
                   @NotNull MailType type,
                   @NotNull Long timestamp,
                   @NotNull List<Component> message) {

}

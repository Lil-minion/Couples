package com.github.lil_minion.server.data;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record Mail(@Nullable UUID sender,
                   @NotNull UUID recipient,
                   @NotNull MailType type,
                   @NotNull Long timestamp,
                   @NotNull List<Component> message) {

}

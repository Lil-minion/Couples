package com.github.lil_minion.network.message;

import com.github.lil_minion.Couples;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a message with the inbox for a player.
 * This record encapsulates a list of mail messages that will be displayed
 * in the inbox.
 */
public record InboxMessage(List<MailMessage> mails) implements CustomPacketPayload {

    public static final ResourceLocation MESSAGE_ID = new ResourceLocation(Couples.MOD_ID + ":send_inbox");
    public static final CustomPacketPayload.Type<InboxMessage> ID = new CustomPacketPayload.Type<>(MESSAGE_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, InboxMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(
                    ArrayList::new,
                    MailMessage.STREAM_CODEC,
                    1000
            ), InboxMessage::mails,
            InboxMessage::new
    );

    /**
     * Returns the type of this custom packet payload.
     *
     * @return the type of the custom packet payload
     */
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

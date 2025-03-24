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
import java.util.Optional;

/**
 * Represents a mail message sent between players.
 * This record encapsulates the details of the mail, including the sender,
 * recipient, mail type, timestamp, message content, and whether it is directed to the server.
 */
public record MailMessage(
        Optional<String> sender,
        String recipient,
        String mailType,
        long timestamp,
        List<String> message,
        boolean toServer
) implements CustomPacketPayload {

    public static final ResourceLocation MESSAGE_ID = new ResourceLocation(Couples.MOD_ID + ":send_mail");
    public static final CustomPacketPayload.Type<MailMessage> ID = new CustomPacketPayload.Type<>(MESSAGE_ID);

    // Define the codec with the values of the message
    public static final StreamCodec<RegistryFriendlyByteBuf, MailMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), MailMessage::sender,
            ByteBufCodecs.STRING_UTF8, MailMessage::recipient,
            ByteBufCodecs.STRING_UTF8, MailMessage::mailType,
            ByteBufCodecs.VAR_LONG, MailMessage::timestamp,

            ByteBufCodecs.collection(
                    ArrayList::new,
                    ByteBufCodecs.STRING_UTF8,
                    16
            ), MailMessage::message,
            ByteBufCodecs.BOOL, MailMessage::toServer,
            MailMessage::new
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

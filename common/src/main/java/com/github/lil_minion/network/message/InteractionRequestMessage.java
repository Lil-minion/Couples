package com.github.lil_minion.network.message;

import com.github.lil_minion.Couples;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


/**
 * Represents an interaction request message sent between players.
 * This record encapsulates the details of the interaction request, including the sender,
 * recipient and interaction request type.
 */
public record InteractionRequestMessage(String sender, String recipient, long timestamp, String interactionType, String message, boolean accepted) implements CustomPacketPayload {

    public static final ResourceLocation MESSAGE_ID = new ResourceLocation(Couples.MOD_ID + ":send_interaction_request");
    public static final CustomPacketPayload.Type<InteractionRequestMessage> ID = new CustomPacketPayload.Type<>(MESSAGE_ID);

    // Define the codec with the values of the message
    public static final StreamCodec<RegistryFriendlyByteBuf, InteractionRequestMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, InteractionRequestMessage::sender,
            ByteBufCodecs.STRING_UTF8, InteractionRequestMessage::recipient,
            ByteBufCodecs.VAR_LONG, InteractionRequestMessage::timestamp,
            ByteBufCodecs.STRING_UTF8, InteractionRequestMessage::interactionType,
            ByteBufCodecs.STRING_UTF8, InteractionRequestMessage::message,
            ByteBufCodecs.BOOL, InteractionRequestMessage::accepted,
            InteractionRequestMessage::new
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




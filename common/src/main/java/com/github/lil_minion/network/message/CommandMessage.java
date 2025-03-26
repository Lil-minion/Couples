package com.github.lil_minion.network.message;

import com.github.lil_minion.Couples;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


/**
 * Represents a message with containing a string.
 */
public record CommandMessage(String message) implements CustomPacketPayload  {

    public static final ResourceLocation MESSAGE_ID = new ResourceLocation(Couples.MOD_ID + ":simple_message");
    public static final CustomPacketPayload.Type<CommandMessage> ID = new CustomPacketPayload.Type<>(MESSAGE_ID);

    // Define the codec with the values of the message
    public static final StreamCodec<RegistryFriendlyByteBuf, CommandMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, CommandMessage::message,
            CommandMessage::new
    );

    /**
     * Returns the type of this custom packet payload.
     *
     * @return the type of the custom packet payload
     */
    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }
    
}

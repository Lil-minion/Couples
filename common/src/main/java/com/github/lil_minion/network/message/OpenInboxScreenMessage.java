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
 * Represents a message to open the inbox screen for a player.
 * This record encapsulates a list of mail messages that will be displayed
 * in the inbox (until local mail storage is properly implemented).
 */
public record OpenInboxScreenMessage(List<MailMessage> mails) implements CustomPacketPayload {

    public static final ResourceLocation MESSAGE_ID = new ResourceLocation(Couples.MOD_ID + ":open_inbox_screen");
    public static final CustomPacketPayload.Type<OpenInboxScreenMessage> ID = new CustomPacketPayload.Type<>(MESSAGE_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenInboxScreenMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(
                    ArrayList::new,
                    MailMessage.STREAM_CODEC,
                    1000
            ), OpenInboxScreenMessage::mails,
            OpenInboxScreenMessage::new
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

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

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }

}

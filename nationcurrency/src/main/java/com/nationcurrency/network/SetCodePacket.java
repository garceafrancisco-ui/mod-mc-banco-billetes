package com.nationcurrency.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.nationcurrency.NationCurrency;

public class SetCodePacket implements CustomPacketPayload {

    public static final Type<SetCodePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(NationCurrency.MOD_ID, "set_code"));

    public static final StreamCodec<ByteBuf, SetCodePacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.stringUtf8(64), SetCodePacket::getCode,
                    SetCodePacket::new);

    private final String code;

    public SetCodePacket(String code) {
        this.code = code;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public String getCode() {
        return code;
    }
}

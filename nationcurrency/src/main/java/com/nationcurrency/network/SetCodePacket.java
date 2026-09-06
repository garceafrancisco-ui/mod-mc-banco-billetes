package com.nationcurrency.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.nationcurrency.NationCurrency;

public class SetCodePacket implements CustomPacketPayload {
    
    public static final Type<SetCodePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(NationCurrency.MOD_ID, "set_code"));
    
    private final String code;
    
    public SetCodePacket(String code) {
        this.code = code;
    }
    
    public SetCodePacket(FriendlyByteBuf buf) {
        this.code = buf.readUtf(64);
    }
    
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(code, 64);
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    
    public String getCode() {
        return code;
    }
}

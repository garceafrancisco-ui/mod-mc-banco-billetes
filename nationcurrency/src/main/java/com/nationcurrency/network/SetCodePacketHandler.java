package com.nationcurrency.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.server.level.ServerPlayer;
import com.nationcurrency.block.BanknotePrinterBlockEntity;

public class SetCodePacketHandler {
    
    public static void handle(SetCodePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            
            if (player.containerMenu instanceof com.nationcurrency.menu.BanknotePrinterMenu menu) {
                BanknotePrinterBlockEntity blockEntity = menu.getBlockEntity();
                if (blockEntity != null) {
                    String code = packet.getCode();
                    
                    // Si el código es "PRINT", iniciamos la impresión
                    if ("PRINT".equals(code)) {
                        blockEntity.startPrinting();
                    } else {
                        // Establecemos el código normalmente
                        blockEntity.setCurrentCode(code);
                    }
                }
            }
        });
    }
}

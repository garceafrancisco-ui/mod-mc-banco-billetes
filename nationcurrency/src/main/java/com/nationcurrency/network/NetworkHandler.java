package com.nationcurrency.network;

import com.nationcurrency.NationCurrency;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = NationCurrency.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

    private static boolean registered = false;

    public static void register() {
        // El registro se hace a través del evento RegisterPayloadHandlersEvent
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        if (registered) return;
        registered = true;
        
        PayloadRegistrar registrar = event.registrar(NationCurrency.MOD_ID);
        registrar.playToServer(SetCodePacket.TYPE, SetCodePacket.STREAM_CODEC, SetCodePacketHandler::handle);
    }
}

package com.nationcurrency.screen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreenEvent;
import com.nationcurrency.menu.BanknotePrinterMenu;
import com.nationcurrency.menu.ModMenus;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModScreens {
    
    public static final ResourceLocation BANKNOTE_PRINTER_TEXTURE = 
            ResourceLocation.fromNamespaceAndPath("nationcurrency", "textures/gui/banknote_printer.png");
    
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreenEvent event) {
        event.register(ModMenus.BANKNOTE_PRINTER_MENU.get(), BanknotePrinterScreen::new);
    }
}

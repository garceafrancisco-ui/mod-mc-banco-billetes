package com.nationcurrency.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = 
            DeferredRegister.create(BuiltInRegistries.MENU, NationCurrency.MOD_ID);
    
    public static final MenuType<BanknotePrinterMenu> BANKNOTE_PRINTER_MENU = 
            IMenuTypeExtension.create((windowId, inv, data) -> new BanknotePrinterMenu(windowId, inv, data));
    
    public static void register() {
        MENUS.register("banknote_printer_menu", () -> BANKNOTE_PRINTER_MENU);
    }
}

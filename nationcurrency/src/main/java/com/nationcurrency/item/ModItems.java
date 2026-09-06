package com.nationcurrency.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItemRegistry(NationCurrency.MOD_ID);
    
    public static final Item BANKNOTE = new BanknoteItem();
    public static final Item COIN = new CoinItem();

    public static void register() {
        ITEMS.register("banknote", () -> BANKNOTE);
        ITEMS.register("coin", () -> COIN);
    }
}

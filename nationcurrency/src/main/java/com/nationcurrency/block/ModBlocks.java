package com.nationcurrency.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NationCurrency.MOD_ID);

    public static final BanknotePrinterBlock BANKNOTE_PRINTER = new BanknotePrinterBlock();
    
    public static void register() {
        BLOCKS.register("banknote_printer", () -> BANKNOTE_PRINTER);
    }
}

package com.nationcurrency.item;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;
import com.nationcurrency.block.ModBlocks;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NationCurrency.MOD_ID);

    public static final DeferredItem<BanknoteItem> BANKNOTE = ITEMS.register("banknote", BanknoteItem::new);
    public static final DeferredItem<CoinItem> COIN = ITEMS.register("coin", CoinItem::new);
    public static final DeferredItem<BlockItem> BANKNOTE_PRINTER_ITEM =
            ITEMS.registerSimpleBlockItem("banknote_printer", ModBlocks.BANKNOTE_PRINTER);
    public static final DeferredItem<BlockItem> PRINTER_FRAME_ITEM =
            ITEMS.registerSimpleBlockItem("printer_frame", ModBlocks.PRINTER_FRAME);

    public static void register() {
        // El registro ya ocurre arriba, al crear los campos.
    }
}

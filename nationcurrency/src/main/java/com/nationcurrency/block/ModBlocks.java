package com.nationcurrency.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NationCurrency.MOD_ID);

    public static final DeferredBlock<BanknotePrinterBlock> BANKNOTE_PRINTER =
            BLOCKS.registerBlock("banknote_printer", BanknotePrinterBlock::new,
                    BlockBehaviour.Properties.of()
                            .strength(50.0f, 1200.0f)
                            .sound(SoundType.NETHERITE_BLOCK)
                            .lightLevel(state -> 8)
                            .requiresCorrectToolForDrops());

    public static final DeferredBlock<PrinterFrameBlock> PRINTER_FRAME =
            BLOCKS.registerBlock("printer_frame", PrinterFrameBlock::new,
                    BlockBehaviour.Properties.of()
                            .strength(10.0f, 20.0f)
                            .sound(SoundType.NETHER_GOLD_ORE)
                            .requiresCorrectToolForDrops());

    public static void register() {
        // El registro ya ocurre arriba, al crear los campos.
    }
}

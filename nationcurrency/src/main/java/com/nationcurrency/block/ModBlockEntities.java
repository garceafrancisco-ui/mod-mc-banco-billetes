package com.nationcurrency.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.nationcurrency.NationCurrency;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NationCurrency.MOD_ID);

    public static void register() {
        BLOCK_ENTITIES.register("banknote_printer", () -> 
            BlockEntityType.Builder.of(BanknotePrinterBlockEntity::new, ModBlocks.BANKNOTE_PRINTER).build(null));
    }
}

package com.nationcurrency.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class BanknotePrinterBlockEntityTicker implements BlockEntityTicker<BanknotePrinterBlockEntity> {
    
    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BanknotePrinterBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }
        
        // Procesar la impresión
        blockEntity.tickPrinting();
    }
}

package com.nationcurrency.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class BanknotePrinterBlockEntityTicker implements BlockEntityTicker<BanknotePrinterBlockEntity> {
    
    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BanknotePrinterBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }
        
        // Slot 0: Papel
        // Slot 1: Pepitas de oro
        // Slot 2: Salida
        
        ItemStack paperStack = blockEntity.getItem(0);
        ItemStack nuggetStack = blockEntity.getItem(1);
        ItemStack outputStack = blockEntity.getItem(2);
        
        if (paperStack.isEmpty() || nuggetStack.isEmpty()) {
            return;
        }
        
        // Verificar si hay espacio en la salida
        if (!outputStack.isEmpty() && outputStack.getCount() >= outputStack.getMaxStackSize()) {
            return;
        }
        
        // Determinar qué producir basado en el código
        String code = blockEntity.getCurrentCode();
        if (code == null || code.isEmpty()) {
            return;
        }
        
        // Crear billete o moneda basado en el código y materiales
        ItemStack result = createBanknote(code, paperStack, nuggetStack);
        
        if (result.isEmpty()) {
            return;
        }
        
        // Verificar compatibilidad con la salida
        if (!outputStack.isEmpty() && !ItemStack.matches(outputStack, result)) {
            return;
        }
        
        // Consumir materiales
        paperStack.shrink(1);
        nuggetStack.shrink(1);
        
        // Producir resultado
        if (outputStack.isEmpty()) {
            blockEntity.setItem(2, result);
        } else {
            outputStack.grow(1);
        }
        
        blockEntity.incrementTotalPrinted();
        blockEntity.setChanged();
    }
    
    private ItemStack createBanknote(String code, ItemStack paper, ItemStack nugget) {
        // Usar el código para generar un billete único
        // El hash del código determinará los colores del billete
        int hashCode = code.hashCode();
        
        // Crear un NBT con la información del billete
        ItemStack banknote = new ItemStack(com.nationcurrency.item.ModItems.BANKNOTE.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("NationCode", code);
        tag.putInt("ColorSeed", hashCode);
        tag.putLong("TotalPrinted", 1);
        banknote.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        return banknote;
    }
}

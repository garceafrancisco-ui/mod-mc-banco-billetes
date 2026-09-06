package com.nationcurrency.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import com.nationcurrency.menu.BanknotePrinterMenu;
import com.nationcurrency.item.ModItems;

public class BanknotePrinterBlockEntity extends BaseContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private String currentCode = "";
    private long totalPrinted = 0;
    private int printProgress = 0;
    private static final int PRINT_TIME = 200; // 10 segundos (20 ticks por segundo)

    public BanknotePrinterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BANKNOTE_PRINTER.get(), pos, state);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.nationcurrency.banknote_printer");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("current_code", currentCode);
        tag.putLong("total_printed", totalPrinted);
        tag.putInt("print_progress", printProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        currentCode = tag.getString("current_code");
        totalPrinted = tag.getLong("total_printed");
        printProgress = tag.getInt("print_progress");
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String code) {
        this.currentCode = code;
        setChanged();
    }

    public long getTotalPrinted() {
        return totalPrinted;
    }

    public void incrementTotalPrinted() {
        this.totalPrinted++;
        setChanged();
    }

    public int getPrintProgress() {
        return printProgress;
    }

    public int getMaxPrintTime() {
        return PRINT_TIME;
    }

    /**
     * Inicia el proceso de impresión
     */
    public void startPrinting() {
        if (canPrint()) {
            this.printProgress = 0;
            setChanged();
        }
    }

    /**
     * Procesa un tick de impresión
     */
    public void tickPrinting() {
        if (!isStructureFormed()) {
            this.printProgress = 0;
            return;
        }

        if (canPrint()) {
            this.printProgress++;
            if (this.printProgress >= PRINT_TIME) {
                completePrinting();
            }
            setChanged();
        } else {
            this.printProgress = 0;
            setChanged();
        }
    }

    /**
     * Verifica si se puede imprimir (hay papel y código establecido)
     */
    private boolean canPrint() {
        ItemStack paper = getItem(0); // Slot de papel
        ItemStack goldNugget = getItem(1); // Slot de pepita de oro
        
        // Se necesita papel y código establecido
        return !paper.isEmpty() && !this.currentCode.isEmpty();
    }

    /**
     * Completa la impresión y genera el billete/moneda
     */
    private void completePrinting() {
        ItemStack paper = getItem(0);
        ItemStack output = getItem(3); // Slot de salida
        
        if (!paper.isEmpty()) {
            // Crear billete con datos
            ItemStack banknote = new ItemStack(ModItems.BANKNOTE.get());
            CompoundTag tag = banknote.getOrCreateTag();
            tag.putString("NationCode", this.currentCode);
            tag.putLong("TotalPrinted", this.totalPrinted + 1);
            
            if (output.isEmpty()) {
                setItem(3, banknote);
            } else if (ItemStack.isSameItem(output, banknote) && 
                       output.hasTag() &&
                       output.getTag().getString("NationCode").equals(this.currentCode)) {
                output.grow(1);
                setItem(3, output);
            } else {
                // No se puede colocar el resultado
                return;
            }
            
            // Consumir papel
            paper.shrink(1);
            setItem(0, paper);
            
            // Incrementar contador
            incrementTotalPrinted();
            this.printProgress = 0;
            setChanged();
        }
    }

    /**
     * Verifica que los 26 bloques que rodean a la impresora (un cubo 3x3x3
     * completo, con la impresora en el centro) sean todos Marco de Impresora.
     */
    public boolean isStructureFormed() {
        if (this.level == null) {
            return false;
        }
        BlockPos center = this.getBlockPos();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    BlockPos checkPos = center.offset(dx, dy, dz);
                    if (!this.level.getBlockState(checkPos).is(ModBlocks.PRINTER_FRAME.get())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.nationcurrency.banknote_printer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new BanknotePrinterMenu(containerId, inventory, this.getBlockPos());
    }
}

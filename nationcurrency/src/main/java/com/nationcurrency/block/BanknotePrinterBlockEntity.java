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

public class BanknotePrinterBlockEntity extends BaseContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private String currentCode = "";
    private long totalPrinted = 0;

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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        currentCode = tag.getString("current_code");
        totalPrinted = tag.getLong("total_printed");
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

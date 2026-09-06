package com.nationcurrency.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BanknotePrinterBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    
    private String currentCode = "";
    private long totalPrinted = 0;
    
    public BanknotePrinterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BANKNOTE_PRINTER.get(), pos, state);
    }

    @Override
    protected Container createContainer() {
        return new SimpleContainer(3);
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.nationcurrency.banknote_printer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new BanknotePrinterMenu(containerId, inventory, this);
    }
}

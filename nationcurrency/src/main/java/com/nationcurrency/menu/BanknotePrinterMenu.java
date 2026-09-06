package com.nationcurrency.menu;

import com.nationcurrency.block.BanknotePrinterBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

public class BanknotePrinterMenu extends AbstractContainerMenu {
    
    private final BanknotePrinterBlockEntity blockEntity;
    private final Level level;
    private final ContainerLevelAccess access;
    
    public BanknotePrinterMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, inventory.player.level(), pos);
    }
    
    public BanknotePrinterMenu(int containerId, Inventory inventory, Level level, BlockPos pos) {
        super(ModMenus.BANKNOTE_PRINTER_MENU.get(), containerId);
        this.level = level;
        this.access = ContainerLevelAccess.create(level, pos);
        
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof BanknotePrinterBlockEntity printer) {
            this.blockEntity = printer;
        } else {
            throw new IllegalArgumentException("Block entity is not a BanknotePrinterBlockEntity");
        }
        
        // Slot para papel (input)
        this.addSlot(new Slot(blockEntity, 0, 80, 20));
        
        // Slot para pepitas de oro (input para monedas)
        this.addSlot(new Slot(blockEntity, 1, 44, 50));
        
        // Slot de salida para billetes/monedas
        this.addSlot(new Slot(blockEntity, 2, 134, 50) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        
        // Inventario del jugador
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        
        // Hotbar del jugador
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }
    
    public BanknotePrinterMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, inventory.player.level(), buf.readBlockPos());
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        
        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            itemstack = original.copy();
            
            if (index < 3) {
                if (!this.moveItemStackTo(original, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(original, 0, 3, false)) {
                return ItemStack.EMPTY;
            }
            
            if (original.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        
        return itemstack;
    }
    
    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.BANKNOTE_PRINTER);
    }
    
    public BanknotePrinterBlockEntity getBlockEntity() {
        return blockEntity;
    }
}

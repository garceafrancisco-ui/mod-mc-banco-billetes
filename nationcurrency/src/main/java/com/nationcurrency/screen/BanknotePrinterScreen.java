package com.nationcurrency.screen;

import com.nationcurrency.block.BanknotePrinterBlockEntity;
import com.nationcurrency.menu.BanknotePrinterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import com.nationcurrency.network.SetCodePacket;

public class BanknotePrinterScreen extends AbstractContainerScreen<BanknotePrinterMenu> {
    
    private EditBox codeInput;
    private Button setCodeButton;
    private Button printButton;
    
    public BanknotePrinterScreen(BanknotePrinterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
    
    @Override
    protected void init() {
        super.init();
        
        int x = this.leftPos + 80;
        int y = this.topPos + 45;
        
        // Crear campo de entrada para el código
        codeInput = new EditBox(this.font, x - 40, y, 80, 20, Component.literal("Código"));
        codeInput.setMaxLength(16);
        codeInput.setValue(getMenu().getBlockEntity().getCurrentCode());
        this.addWidget(codeInput);
        
        // Botón para establecer código
        setCodeButton = Button.builder(Component.literal("Establecer Código"), 
                btn -> sendSetCodePacket())
                .bounds(x - 40, y + 25, 80, 20)
                .build();
        this.addWidget(setCodeButton);
        
        // Botón para imprimir
        printButton = Button.builder(Component.literal("Imprimir"), 
                btn -> sendPrintPacket())
                .bounds(x - 40, y + 50, 80, 20)
                .build();
        this.addWidget(printButton);
    }
    
    private void sendSetCodePacket() {
        String code = codeInput.getValue();
        PacketDistributor.sendToServer(new SetCodePacket(code));
    }
    
    private void sendPrintPacket() {
        // Enviar paquete especial para iniciar impresión
        PacketDistributor.sendToServer(new SetCodePacket("PRINT"));
    }
    
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        
        // Dibujar fondo del menú
        guiGraphics.blit(ModScreens.BANKNOTE_PRINTER_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
    
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        
        // Mostrar información de billetes impresos
        long totalPrinted = getMenu().getBlockEntity().getTotalPrinted();
        guiGraphics.drawString(this.font, "Total: " + totalPrinted, 8, 60, 0x404040, false);
        
        // Mostrar barra de progreso
        int progress = getMenu().getBlockEntity().getPrintProgress();
        int maxProgress = getMenu().getBlockEntity().getMaxPrintTime();
        if (progress > 0) {
            String progressText = "Progreso: " + (progress * 100 / maxProgress) + "%";
            guiGraphics.drawString(this.font, progressText, 8, 72, 0x404040, false);
            
            // Dibujar barra de progreso
            int barWidth = (progress * 60) / maxProgress;
            guiGraphics.fill(8, 82, 8 + barWidth, 86, 0xFF00FF00);
            guiGraphics.fill(8 + barWidth, 82, 68, 86, 0xFF404040);
        }
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (codeInput.isFocused()) {
            return codeInput.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        codeInput.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }
}

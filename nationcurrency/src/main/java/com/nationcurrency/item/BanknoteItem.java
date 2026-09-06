package com.nationcurrency.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BanknoteItem extends Item {
    
    public BanknoteItem() {
        super(new Properties().stacksTo(64));
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (stack.hasTag()) {
            String code = stack.getTag().getString("NationCode");
            long printCount = stack.getTag().getLong("TotalPrinted");
            
            tooltip.add(Component.literal("Código de Nación: " + code).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.literal("Total Imprimido: " + printCount).withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Billete Único").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }
}

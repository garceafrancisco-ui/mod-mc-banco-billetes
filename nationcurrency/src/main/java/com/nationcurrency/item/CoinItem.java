package com.nationcurrency.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CoinItem extends Item {
    
    public CoinItem() {
        super(new Properties().stacksTo(64));
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (stack.hasTag()) {
            String code = stack.getTag().getString("NationCode");
            int value = stack.getTag().getInt("CoinValue");
            
            tooltip.add(Component.literal("Código de Nación: " + code).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.literal("Valor: " + value).withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Moneda de Oro").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW));
        }
    }
}

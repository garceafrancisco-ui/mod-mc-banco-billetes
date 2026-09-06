package com.nationcurrency.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class CoinItem extends Item {

    public CoinItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            String code = tag.getString("NationCode");
            int value = tag.getInt("CoinValue");

            tooltip.add(Component.literal("Código de Nación: " + code).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.literal("Valor: " + value).withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("Moneda de Oro").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW));
        }
    }
}

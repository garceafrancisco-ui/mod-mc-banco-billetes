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

public class BanknoteItem extends Item {

    public BanknoteItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            String code = tag.getString("NationCode");
            long printCount = tag.getLong("TotalPrinted");

            if (!code.isEmpty()) {
                tooltip.add(Component.literal("Código de Nación: " + code).withStyle(ChatFormatting.GOLD));
            }
            if (printCount > 0) {
                tooltip.add(Component.literal("Total Imprimido: " + printCount).withStyle(ChatFormatting.GREEN));
            }
            tooltip.add(Component.literal("Billete Único").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }
}

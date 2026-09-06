package com.nationcurrency;

import com.mojang.logging.LogUtils;
import com.nationcurrency.block.ModBlocks;
import com.nationcurrency.block.ModBlockEntities;
import com.nationcurrency.item.ModItems;
import com.nationcurrency.menu.ModMenus;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(NationCurrency.MOD_ID)
public class NationCurrency {
    public static final String MOD_ID = "nationcurrency";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NationCurrency(IEventBus modEventBus) {
        modEventBus.register(this);
        
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        
        LOGGER.info("Nation Currency mod initialized!");
    }

    @SubscribeEvent
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.BANKNOTE_PRINTER);
        }
    }
}

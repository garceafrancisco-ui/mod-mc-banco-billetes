package com.nationcurrency.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

public class PrinterFrameBlock extends Block {

    public static final MapCodec<PrinterFrameBlock> CODEC = simpleCodec(PrinterFrameBlock::new);

    public PrinterFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends PrinterFrameBlock> codec() {
        return CODEC;
    }
}

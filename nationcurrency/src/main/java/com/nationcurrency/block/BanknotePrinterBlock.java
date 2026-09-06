package com.nationcurrency.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BanknotePrinterBlock extends BaseEntityBlock {

    public static final MapCodec<BanknotePrinterBlock> CODEC = simpleCodec(BanknotePrinterBlock::new);

    public BanknotePrinterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BanknotePrinterBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BanknotePrinterBlockEntity(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BanknotePrinterBlockEntity printer) {
                if (!printer.isStructureFormed()) {
                    player.displayClientMessage(Component.translatable("message.nationcurrency.structure_incomplete"), true);
                    return InteractionResult.FAIL;
                }
                serverPlayer.openMenu(printer, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            // La estructura 3x3x3 se verifica automáticamente cuando se usa el bloque
            // Si la impresora se rompe, la estructura deja de ser válida
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public <T extends net.minecraft.world.level.block.entity.BlockEntity> net.minecraft.world.level.block.entity.BlockEntityTicker<T> getTicker(Level level, BlockState state) {
        if (level.isClientSide) {
            return null;
        }
        return (net.minecraft.world.level.block.entity.BlockEntityTicker<T>) new BanknotePrinterBlockEntityTicker();
    }
}

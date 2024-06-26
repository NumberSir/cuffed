package com.lazrproductions.cuffed.items;

import javax.annotation.Nonnull;

import com.lazrproductions.cuffed.entity.PadlockEntity;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class Padlock extends Item {
    public Padlock(Properties p) {
        super(p);
    }

    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;

        Level level = context.getLevel();
        if (!level.isClientSide && context.getHand() == InteractionHand.MAIN_HAND)
            if (level.getBlockState(context.getClickedPos()).is(ModTags.Blocks.LOCKABLE_BLOCKS)) {
                BlockPos pos = context.getClickedPos();
                if(PadlockEntity.getLockAt(level, pos) != null)
                    return InteractionResult.FAIL;
                else{
                    player.level().playSound(null, pos, SoundEvents.CHAIN_PLACE, SoundSource.BLOCKS);
                    PadlockEntity.getOrCreateLockAt(level, pos, context.getClickedFace());
                    player.awardStat(Stats.ITEM_USED.get(ModItems.PADLOCK.get()));
                    player.getItemInHand(context.getHand()).shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemstack = new ItemStack(this);
        return itemstack;
    }
}
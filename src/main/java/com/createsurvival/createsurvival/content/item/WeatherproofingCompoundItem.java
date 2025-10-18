package com.createsurvival.createsurvival.content.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Used to reset the weathering level on {@link com.createsurvival.createsurvival.content.block.WeatherproofCasingBlock}.
 */
public class WeatherproofingCompoundItem extends Item {
    public WeatherproofingCompoundItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            level.playSound(null, player, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.6f, 0.9f);
        }
        return InteractionResultHolder.pass(stack);
    }

    public void consumeCompound(ItemStack stack, Player player) {
        Level level = player.level();
        if (!level.isClientSide) {
            level.playSound(null, player.blockPosition(), SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 0.6f, 1.0f);
            stack.shrink(1);
        }
    }
}

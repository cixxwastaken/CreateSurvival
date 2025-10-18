package com.createsurvival.createsurvival.content.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * A reusable emergency kit that can be prepared in advance. Once primed, it grants the player a burst of energy along
 * with much-needed saturation and resistance to harsh weather while exploring Create contraptions.
 */
public class SurvivalKitItem extends Item {
    private static final String TAG_PRIMED = "Primed";

    public SurvivalKitItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        boolean primed = tag.getBoolean(TAG_PRIMED);
        if (!level.isClientSide) {
            if (!primed) {
                tag.putBoolean(TAG_PRIMED, true);
                level.playSound(null, player.blockPosition(), SoundEvents.BUNDLE_INSERT, SoundSource.PLAYERS, 0.5f, 1.1f);
            } else {
                triggerEffects(player, stack);
                tag.putBoolean(TAG_PRIMED, false);
            }
        }
        return InteractionResultHolder.success(stack);
    }

    private void triggerEffects(Player player, ItemStack stack) {
        Level level = player.level();
        level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.7f, 1.2f);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 1, 1));
        if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
        }
    }
}

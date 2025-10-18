package com.createsurvival.createsurvival.events;

import com.createsurvival.createsurvival.CreateSurvival;
import com.createsurvival.createsurvival.compat.ImmersiveWeatheringCompat;
import com.createsurvival.createsurvival.compat.SereneSeasonsCompat;
import com.createsurvival.createsurvival.content.CSBlocks;
import com.createsurvival.createsurvival.content.CSItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent.CropGrowEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = CreateSurvival.MOD_ID)
public final class CSEvents {
    private CSEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        Level level = player.level();
        if (!ImmersiveWeatheringCompat.isHarsh(level)) {
            return;
        }
        if (!level.canSeeSky(player.blockPosition()) || player.isCreative()) {
            return;
        }
        if (isCarryingWeatherproofing(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 0, false, false));
            return;
        }
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false));
    }

    private static boolean isCarryingWeatherproofing(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(CSItems.WEATHERPROOFING_COMPOUND.get())) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onCropGrow(CropGrowEvent.Pre event) {
        if (!(event.getLevel() instanceof Level level)) {
            return;
        }
        Optional<String> season = SereneSeasonsCompat.getSeasonName(level);
        season.ifPresent(current -> {
            if (current.contains("winter")) {
                event.setResult(Event.Result.DENY);
            } else if (current.contains("spring")) {
                if (level.random.nextFloat() < 0.35f) {
                    event.setResult(Event.Result.ALLOW);
                }
            }
        });

        if (!event.isCanceled() && event.getResult() != Event.Result.DENY) {
            if (isNearWeatherproofing(event.getPos(), level)) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    private static boolean isNearWeatherproofing(BlockPos pos, Level level) {
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, 1, 2))) {
            BlockState state = level.getBlockState(targetPos);
            if (state.is(CSBlocks.WEATHERPROOF_CASING.get())) {
                return true;
            }
        }
        return false;
    }
}

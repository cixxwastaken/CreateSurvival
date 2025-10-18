package com.createsurvival.createsurvival.compat;

import com.createsurvival.createsurvival.CreateSurvival;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

import java.lang.reflect.Method;

public final class ThirstCompat {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String MOD_ID = "thirstwasTaken";
    private static boolean active;
    private static Method hydrateMethod;

    private ThirstCompat() {
    }

    public static void bootstrap() {
        active = ModList.get().isLoaded(MOD_ID);
        if (!active) {
            LOGGER.debug("Thirst Was Taken not detected. Hydration hooks disabled.");
            return;
        }
        try {
            Class<?> helperClass = Class.forName("com.anthonyhilyard.thirst.ThirstHelper");
            hydrateMethod = helperClass.getMethod("addWater", Player.class, int.class);
            LOGGER.info("[{}] Thirst Was Taken integration enabled", CreateSurvival.MOD_ID);
        } catch (Exception exception) {
            LOGGER.warn("Failed to bind Thirst Was Taken integration", exception);
            active = false;
        }
    }

    public static void hydrate(Player player) {
        if (!active) {
            // Provide a small vanilla benefit so the flask remains valuable without the thirst mod.
            player.getFoodData().eat(1, 0.2f);
            return;
        }
        if (hydrateMethod == null) {
            return;
        }
        try {
            hydrateMethod.invoke(null, player, 4);
        } catch (Exception exception) {
            LOGGER.debug("Hydration invocation failed", exception);
            active = false;
            player.getFoodData().eat(1, 0.2f);
        }
    }
}

package com.createsurvival.createsurvival.compat;

import com.createsurvival.createsurvival.CreateSurvival;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Optional;

public final class SereneSeasonsCompat {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String MOD_ID = "sereneseasons";
    private static boolean active;
    private static Method seasonStateMethod;
    private static Method seasonValueMethod;

    private SereneSeasonsCompat() {
    }

    public static void bootstrap() {
        active = ModList.get().isLoaded(MOD_ID);
        if (!active) {
            LOGGER.debug("Serene Seasons not detected. Seasonal hooks disabled.");
            return;
        }
        try {
            Class<?> helperClass = Class.forName("sereneseasons.api.season.SeasonHelper");
            seasonStateMethod = helperClass.getMethod("getSeasonState", Level.class);
            Class<?> seasonState = Class.forName("sereneseasons.api.season.SeasonState");
            seasonValueMethod = seasonState.getMethod("getSeason");
            LOGGER.info("[{}] Serene Seasons integration enabled", CreateSurvival.MOD_ID);
        } catch (Exception exception) {
            LOGGER.warn("Failed to wire Serene Seasons integration", exception);
            active = false;
        }
    }

    public static Optional<String> getSeasonName(Level level) {
        if (!active || seasonStateMethod == null || seasonValueMethod == null) {
            return Optional.empty();
        }
        try {
            Object state = seasonStateMethod.invoke(null, level);
            if (state == null) {
                return Optional.empty();
            }
            Object season = seasonValueMethod.invoke(state);
            if (season == null) {
                return Optional.empty();
            }
            return Optional.of(season.toString().toLowerCase(Locale.ROOT));
        } catch (Exception exception) {
            LOGGER.debug("Unable to read Serene Seasons state", exception);
            active = false;
            return Optional.empty();
        }
    }
}

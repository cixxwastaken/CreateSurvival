package com.createsurvival.createsurvival.compat;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

public final class ImmersiveWeatheringCompat {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String MOD_ID = "immersive_weathering";
    private static boolean active;

    private ImmersiveWeatheringCompat() {
    }

    public static void bootstrap() {
        active = ModList.get().isLoaded(MOD_ID);
        if (active) {
            LOGGER.info("Immersive Weathering detected. Intensifying weather penalties.");
        } else {
            LOGGER.debug("Immersive Weathering not detected. Using vanilla weather strength.");
        }
    }

    public static boolean isHarsh(Level level) {
        if (!active) {
            return level.isThundering();
        }
        return level.isThundering() || level.getRainLevel(1.0f) > 0.75f;
    }
}

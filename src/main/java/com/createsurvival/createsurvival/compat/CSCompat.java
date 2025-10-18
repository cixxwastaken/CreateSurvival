package com.createsurvival.createsurvival.compat;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class CSCompat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CSCompat() {
    }

    public static void bootstrap() {
        SereneSeasonsCompat.bootstrap();
        ThirstCompat.bootstrap();
        ImmersiveWeatheringCompat.bootstrap();
        LOGGER.debug("Create: Survival compatibility initialized");
    }
}

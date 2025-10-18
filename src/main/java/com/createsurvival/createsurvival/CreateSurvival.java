package com.createsurvival.createsurvival;

import com.createsurvival.createsurvival.compat.CSCompat;
import com.createsurvival.createsurvival.content.CSBlocks;
import com.createsurvival.createsurvival.content.CSCreativeTabs;
import com.createsurvival.createsurvival.content.CSItems;
import com.createsurvival.createsurvival.content.CSRecipeSerializers;
import com.createsurvival.createsurvival.network.CSNetworking;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Entry point for the Create: Survival addon. The goal for this mod is to extend Create's mechanical sandbox with
 * progression and maintenance systems that feel at home in survival-oriented modpacks.
 */
@Mod(CreateSurvival.MOD_ID)
public class CreateSurvival {
    public static final String MOD_ID = "createsurvival";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateSurvival() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CSBlocks.register(modEventBus);
        CSItems.register(modEventBus);
        CSCreativeTabs.register(modEventBus);
        CSRecipeSerializers.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CSNetworking.init();
            CSCompat.bootstrap();
        });
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Create: Survival client setup complete");
    }
}

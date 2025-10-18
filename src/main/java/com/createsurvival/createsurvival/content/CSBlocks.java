package com.createsurvival.createsurvival.content;

import com.createsurvival.createsurvival.CreateSurvival;
import com.createsurvival.createsurvival.content.block.WeatherproofCasingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateSurvival.MOD_ID);

    public static final RegistryObject<Block> WEATHERPROOF_CASING = BLOCKS.register("weatherproof_casing", WeatherproofCasingBlock::new);

    private CSBlocks() {
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}

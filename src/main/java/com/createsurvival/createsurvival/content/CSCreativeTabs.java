package com.createsurvival.createsurvival.content;

import com.createsurvival.createsurvival.CreateSurvival;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CSCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSurvival.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SURVIVAL_TAB = TABS.register("survival",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.createsurvival.survival"))
                    .withTabsBefore(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                    .icon(() -> CSItems.SURVIVAL_KIT.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(CSItems.WEATHERPROOF_CASING.get());
                        output.accept(CSItems.WEATHERPROOFING_COMPOUND.get());
                        output.accept(CSItems.PURIFIED_WATER_FLASK.get());
                        output.accept(CSItems.FIELD_RATION.get());
                        output.accept(CSItems.SURVIVAL_KIT.get());
                        output.accept(CSItems.RUST_FLAKES.get());
                    })
                    .build());

    private CSCreativeTabs() {
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}

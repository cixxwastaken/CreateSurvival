package com.createsurvival.createsurvival.content;

import com.createsurvival.createsurvival.CreateSurvival;
import com.createsurvival.createsurvival.content.item.FieldRationItem;
import com.createsurvival.createsurvival.content.item.PurifiedWaterFlaskItem;
import com.createsurvival.createsurvival.content.item.RustFlakesItem;
import com.createsurvival.createsurvival.content.item.SurvivalKitItem;
import com.createsurvival.createsurvival.content.item.WeatherproofingCompoundItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateSurvival.MOD_ID);

    public static final RegistryObject<Item> WEATHERPROOF_CASING = ITEMS.register("weatherproof_casing",
            () -> new BlockItem(CSBlocks.WEATHERPROOF_CASING.get(), new Item.Properties()));

    public static final RegistryObject<Item> RUST_FLAKES = ITEMS.register("rust_flakes",
            () -> new RustFlakesItem(new Item.Properties()));

    public static final RegistryObject<Item> WEATHERPROOFING_COMPOUND = ITEMS.register("weatherproofing_compound",
            () -> new WeatherproofingCompoundItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> PURIFIED_WATER_FLASK = ITEMS.register("purified_water_flask",
            () -> new PurifiedWaterFlaskItem(new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> FIELD_RATION = ITEMS.register("field_ration",
            () -> new FieldRationItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> SURVIVAL_KIT = ITEMS.register("survival_kit",
            () -> new SurvivalKitItem(new Item.Properties().durability(64)));

    private CSItems() {
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

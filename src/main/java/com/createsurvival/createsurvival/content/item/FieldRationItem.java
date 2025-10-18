package com.createsurvival.createsurvival.content.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FieldRationItem extends Item {
    private static final FoodProperties FOOD = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(0.9f)
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 400, 0), 1.0f)
            .build();

    public FieldRationItem(Properties properties) {
        super(properties.food(FOOD));
    }
}

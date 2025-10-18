package com.createsurvival.createsurvival.network;

import com.createsurvival.createsurvival.CreateSurvival;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class CSNetworking {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;

    private CSNetworking() {
    }

    public static void init() {
        if (channel != null) {
            return;
        }
        channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(CreateSurvival.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);
    }

    public static SimpleChannel getChannel() {
        return channel;
    }
}

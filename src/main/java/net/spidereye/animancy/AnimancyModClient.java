package net.spidereye.animancy;

import net.fabricmc.api.ClientModInitializer;
import net.spidereye.animancy.networking.ModPackets;

public class AnimancyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CPackets();
    }
}

package net.spidereye.animancy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.spidereye.animancy.client.AnimancyHudOverlay;
import net.spidereye.animancy.event.KeyInputHandler;
import net.spidereye.animancy.event.PlayerClientStartWorldTickHandler;
import net.spidereye.animancy.networking.ModPackets;

public class AnimancyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CPackets();

        KeyInputHandler.register();
        HudRenderCallback.EVENT.register(new AnimancyHudOverlay());

        ClientTickEvents.START_WORLD_TICK.register(new PlayerClientStartWorldTickHandler());
    }
}

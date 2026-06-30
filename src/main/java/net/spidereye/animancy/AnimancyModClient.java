package net.spidereye.animancy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.spidereye.animancy.client.AnimancerHudOverlay;
import net.spidereye.animancy.client.SoulRipHudOverlay;
import net.spidereye.animancy.entity.ModEntities;
import net.spidereye.animancy.entity.client.renderer.DregZombieRenderer;
import net.spidereye.animancy.event.KeyInputHandler;
import net.spidereye.animancy.event.PlayerClientStartWorldTickHandler;
import net.spidereye.animancy.networking.ModPackets;

public class AnimancyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CPackets();

        KeyInputHandler.register();
        HudRenderCallback.EVENT.register(new AnimancerHudOverlay());
        HudRenderCallback.EVENT.register(new SoulRipHudOverlay());

        ClientTickEvents.START_WORLD_TICK.register(new PlayerClientStartWorldTickHandler());

        EntityRendererRegistry.register(ModEntities.DREG, DregZombieRenderer::new);
    }
}

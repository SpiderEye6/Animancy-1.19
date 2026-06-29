package net.spidereye.animancy.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class PlayerClientStartWorldTickHandler implements ClientTickEvents.StartWorldTick{
    @Override
    public void onStartTick(ClientWorld world) {
        for (AbstractClientPlayerEntity player : world.getPlayers()) {
            if(!SoulUtil.syncedWithServer((IEntityDataSaver) player)) {
                SoulUtil.syncOnJoin((IEntityDataSaver) player);
            }
        }
    }
}

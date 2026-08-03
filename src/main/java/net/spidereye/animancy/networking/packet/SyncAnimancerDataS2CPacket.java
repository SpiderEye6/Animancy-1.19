package net.spidereye.animancy.networking.packet;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.spidereye.animancy.util.IEntityDataSaver;

import java.util.UUID;

public class SyncAnimancerDataS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            boolean isAnimancer = buf.readBoolean();
            UUID uuid = buf.readUuid();
            if (uuid == null) {
                ((IEntityDataSaver) client.player).getPersistentData().putBoolean("is_animancer", isAnimancer);
            } else {
                ((IEntityDataSaver) client.world.getPlayerByUuid(uuid)).getPersistentData().putBoolean("is_animancer", isAnimancer);
            }
        }
    }
}

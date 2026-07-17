package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.spidereye.animancy.util.IEntityDataSaver;

public class SyncSoulDataS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            ((IEntityDataSaver) client.player).getPersistentData().putDouble("soul_size", buf.readDouble());
        }
    }
}

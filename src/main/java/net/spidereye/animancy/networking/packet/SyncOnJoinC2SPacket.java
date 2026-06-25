package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class SyncOnJoinC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler,
                                                             PacketByteBuf buf, PacketSender responseSender) {
        double soul = SoulData.getSoul((IEntityDataSaver) entity);
        boolean isAnimancer = SoulData.isAnimancer((IEntityDataSaver) entity);

        SoulData.syncSoul(soul, entity);
        SoulData.syncAnimancer(isAnimancer, entity);
    }
}

package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class SyncOnJoinC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler,
                                                             PacketByteBuf buf, PacketSender responseSender) {
        double soul = SoulUtil.getSoul((IEntityDataSaver) entity);
        boolean isAnimancer = SoulUtil.isAnimancer((IEntityDataSaver) entity);
        double soulRipCounter = SoulUtil.getSoulRipCounter((IEntityDataSaver) entity);

        SoulUtil.syncSoul(soul, entity);
        SoulUtil.syncAnimancer(isAnimancer, entity);
        SoulUtil.syncSoulRipCounter(soulRipCounter, entity);
    }
}

package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.RaycastUtil;
import net.spidereye.animancy.util.SoulUtil;

public class SoulRipC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler,
                                                             PacketByteBuf buf, PacketSender responseSender) {
        double soulPower = SoulUtil.soulPower((IEntityDataSaver) entity);
        Entity entity1 = RaycastUtil.raycastEntity(entity.getCameraEntity(), soulPower, 1.0f);
        if (entity1 instanceof LivingEntity victim) {
            SoulUtil.addSoulRipCounter((IEntityDataSaver) victim, soulPower);
            if (SoulUtil.getSoulRipCounter((IEntityDataSaver) victim) >= victim.getMaxHealth()) {
                SoulUtil.killWithAnimancy(entity, victim);
            }
        }
    }
}

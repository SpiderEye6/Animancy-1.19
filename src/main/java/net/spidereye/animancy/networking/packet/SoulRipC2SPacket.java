package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
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
            if (victim instanceof PlayerEntity player) {
                if (player.isCreative() || player.isSpectator()) {
                    return;
                }
            }

            if (victim instanceof WardenEntity) {
                return;
            }

            SoulUtil.addSoulRipCounter((IEntityDataSaver) victim, soulPower);

            double difficulty = victim.getMaxHealth();
            if (SoulUtil.isAnimancer((IEntityDataSaver) victim)) {
                difficulty = Math.max(victim.getMaxHealth(), SoulUtil.getSoul((IEntityDataSaver) victim));
            }

            if (SoulUtil.getSoulRipCounter((IEntityDataSaver) victim) >= difficulty) {
                SoulUtil.killWithAnimancy(entity, victim);
            }
        }
    }
}

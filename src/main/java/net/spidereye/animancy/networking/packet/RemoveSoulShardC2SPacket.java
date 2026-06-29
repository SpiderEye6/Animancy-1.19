package net.spidereye.animancy.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class RemoveSoulShardC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ItemStack shard = new ItemStack(ModItems.SOUL_SHARD);
        int count;
        World world = entity.getWorld();

        if (entity.isSneaking()) {
            int soulSize = (int) SoulUtil.getSoul((IEntityDataSaver) entity);
            if (soulSize <= 64) {
                count = soulSize - 1;
            } else {
                count = 64;
            }
        } else {
            count = 1;
        }

        shard.setCount(count);
        SoulUtil.removeSoul((IEntityDataSaver) entity, count);

        if (!entity.getInventory().insertStack(shard)) {
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), shard));
        }
        // Play sound?
    }
}

package net.spidereye.animancy.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spidereye.animancy.networking.ModPackets;

public class SoulData {
    public static double addSoul(IEntityDataSaver player, double amount) {
        NbtCompound nbt = player.getPersistentData();
        double soul = nbt.getDouble("soul_size");
        soul += amount;
        nbt.putDouble("soul_size", soul);
        syncSoul(soul, (ServerPlayerEntity) player);
        return soul;
    }

    public static double removeSoul(IEntityDataSaver player, double amount) {
        NbtCompound nbt = player.getPersistentData();
        double soul = nbt.getDouble("soul_size");
        if (soul - amount <= 0) {
            soul = 0.1D;
        } else {
            soul -= amount;
        }
        nbt.putDouble("soul_size", soul);
        syncSoul(soul, (ServerPlayerEntity) player);
        return soul;
    }

    public static double setSoul(IEntityDataSaver player, double amount) {
        NbtCompound nbt = player.getPersistentData();
        double soul = nbt.getDouble("soul_size");
        soul = amount;
        nbt.putDouble("soul_size", soul);
        syncSoul(soul, (ServerPlayerEntity) player);
        return soul;
    }

    public static double getSoul(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        double soul = nbt.getDouble("soul_size");
        return soul;
    }

    public static void syncSoul(double soul, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(soul);
        ServerPlayNetworking.send(player, ModPackets.SOUL_DATA_SYNC, buffer);
    }

    public static void setAnimancer(IEntityDataSaver player, boolean isAnimancer) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("is_animancer", isAnimancer);
        syncAnimancer(isAnimancer, (ServerPlayerEntity) player);
    }

    public static boolean isAnimancer(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean("is_animancer");
    }

    public static void syncAnimancer(boolean isAnimancer, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(isAnimancer);
        ServerPlayNetworking.send(player, ModPackets.ANIMANCER_DATA_SYNC, buffer);
    }
}

package net.spidereye.animancy.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.networking.ModPackets;

import java.util.Map;

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
        double soul = amount;
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

    public static double addSoul(ItemStack item, ServerPlayerEntity owner, double amount) {
        NbtCompound nbt = item.getOrCreateNbt();
        double soul = nbt.getDouble("size");
        soul += amount;
        nbt.putDouble("size", soul);
        syncSoul(soul, item, owner);
        return soul;
    }

    public static double removeSoul(ItemStack item, ServerPlayerEntity owner, double amount) {
        NbtCompound nbt = item.getOrCreateNbt();
        double soul = nbt.getDouble("size");
        if (soul - amount <= 0) {
            soul = 0.1D;
        } else {
            soul -= amount;
        }
        nbt.putDouble("size", soul);
        syncSoul(soul, item, owner);
        return soul;
    }

    public static double setSoul(ItemStack item, double amount) {
        item.getOrCreateNbt().putDouble("size", amount);
        return amount;
    }

    public static double getSoul(ItemStack item) {
        NbtCompound nbt = item.getNbt();
        if (nbt != null) {
            return nbt.getDouble("size");
        }

        return 0.0D;
    }

    public static void syncSoul(double soul, ItemStack item, ServerPlayerEntity owner) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeItemStack(item);
        buffer.writeDouble(soul);
        ServerPlayNetworking.send(owner, ModPackets.SOUL_DATA_SYNC, buffer);
    }

    public static boolean hasEnchantment(ItemStack item, Enchantment enchantment) {
        if (item.hasEnchantments()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(item);
            if (enchantments.containsKey(enchantment)) {
                return true;
            }
        }
        return false;
    }

    public static double addHealthModifier(LivingEntity entity, double amount, String name) {
        Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = ArrayListMultimap.create();
        attributeModifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(name,
                amount, EntityAttributeModifier.Operation.ADDITION));
        entity.getAttributes().addTemporaryModifiers(attributeModifiers);
        return entity.getMaxHealth();
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

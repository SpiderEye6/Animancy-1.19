package net.spidereye.animancy.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.item.ModToolMaterial;
import net.spidereye.animancy.networking.ModPackets;

import java.util.Map;

public class SoulUtil {
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
        syncAnimancer(isAnimancer((IEntityDataSaver) player), player);
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

    public static double addSoulRipCounter(IEntityDataSaver entity, double amount) {
        NbtCompound nbt = entity.getPersistentData();
        double soulRipCounter = nbt.getDouble("soulrip_counter");
        soulRipCounter += amount;
        nbt.putDouble("soulrip_counter", soulRipCounter);
        if (entity instanceof PlayerEntity player) {
            syncSoulRipCounter(soulRipCounter, (ServerPlayerEntity) player);
        }
        return soulRipCounter;
    }

    public static double removeSoulRipCounter(IEntityDataSaver entity, double amount) {
        NbtCompound nbt = entity.getPersistentData();
        double soulRipCounter = nbt.getDouble("soulrip_counter");
        if (soulRipCounter - amount <= 0) {
            soulRipCounter = 0.0D;
        } else {
            soulRipCounter -= amount;
        }
        nbt.putDouble("soulrip_counter", soulRipCounter);
        if (entity instanceof PlayerEntity player) {
            syncSoulRipCounter(soulRipCounter, (ServerPlayerEntity) player);
        }
        return soulRipCounter;
    }

    public static double setSoulRipCounter(IEntityDataSaver entity, double amount) {
        NbtCompound nbt = entity.getPersistentData();
        double soulRipCounter = amount;
        nbt.putDouble("soulrip_counter", soulRipCounter);
        if (entity instanceof PlayerEntity player) {
            syncSoulRipCounter(soulRipCounter, (ServerPlayerEntity) player);
        }
        return soulRipCounter;
    }

    public static double getSoulRipCounter(IEntityDataSaver entity) {
        NbtCompound nbt = entity.getPersistentData();
        double soulRipCounter = nbt.getDouble("soulrip_counter");
        return soulRipCounter;
    }

    public static void syncSoulRipCounter(double soulRipCounter, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(soulRipCounter);
        ServerPlayNetworking.send(player, ModPackets.SYNC_SOUL_RIP_COUNTER, buffer);
    }

    public static float getSoulRipPercentage(IEntityDataSaver entity) {
        double soulRipCounter = getSoulRipCounter(entity);
        double soulSize = getSoul(entity);
        double maxHealth = ((LivingEntity) entity).getMaxHealth();
        float percentage;

        if (isAnimancer(entity) && (soulSize > maxHealth)) {
            percentage = (float) (soulRipCounter / soulSize);
        } else {
            percentage = (float) (soulRipCounter / maxHealth);
        }

        if (percentage > 1.0f) {
            percentage = 1.0f;
        } else if (percentage < 0.0f) {
            percentage = 0.0f;
        }

        return percentage;
    }

    public static double soulRange(IEntityDataSaver entity) {
        return getSoul(entity) / 100;
    }

    public static double soulPower(IEntityDataSaver entity) {
        return getSoul(entity) / 100;
    }

    public static void emitSoulGroan(LivingEntity entity, float volume) {
        if (entity.getWorld() instanceof ServerWorld world) {
            BlockPos pos = entity.getBlockPos();
            float pitch = (float) (0.5 + (0.5 * getSoulRipCounter((IEntityDataSaver) entity)));
            world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.PLAYERS, volume, pitch);
        }
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

    public static void setSyncedWithServer(IEntityDataSaver player, boolean synced) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("should_sync_on_join", synced);
    }

    public static boolean syncedWithServer(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean("should_sync_on_join");
    }

    public static void syncOnJoin(IEntityDataSaver player) {
        ClientPlayNetworking.send(ModPackets.SYNC_ON_JOIN, PacketByteBufs.create());
        setSyncedWithServer(player, true);
    }

    public static void playEatSoulSound(ServerWorld world, BlockPos pos, float volume, float pitch) {
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, volume, pitch);
    }

    public static ItemStack makeSoulItemVariant(double size) {
        ItemStack soul;
        if (size >= 200) {
            soul = new ItemStack(ModItems.DRAGON_SOUL);
        } else if (size > 1.0) {
            soul = new ItemStack(ModItems.SOUL);
            setSoul(soul, size);
        } else {
            soul = new ItemStack(ModItems.SOUL_SHARD);
        }
        return soul;
    }

    public static ItemStack makeRevenantSoulItemVariant(double size) {
        ItemStack revenantSoul;
        if (size >= 200) {
            revenantSoul = new ItemStack(ModItems.DRACONIC_RISEN_SOUL);
        } else if (size > 1.0) {
            revenantSoul = new ItemStack(ModItems.REVENANT_SOUL);
            setSoul(revenantSoul, size);
        } else {
            revenantSoul = new ItemStack(ModItems.SOUL_SHARD);
        }
        return revenantSoul;
    }

    public static boolean isAnimanticWeapon(ItemStack item) {
        if (item.getItem() instanceof ToolItem weapon) {
            return weapon.getMaterial() == ModToolMaterial.SOUL_STEEL;
        }
        return false;
    }

    public static void killWithAnimancy(PlayerEntity attacker, LivingEntity victim) {
        attacker.getWorld().playSound(null, victim.getBlockPos(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK,
                SoundCategory.PLAYERS, 1.0f, 0.1f);
        victim.damage(DamageSource.player(attacker), Float.MAX_VALUE);
    }
}

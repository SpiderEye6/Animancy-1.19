package net.spidereye.animancy.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.networking.packet.*;

public class ModPackets {
    public static final Identifier SOUL_DATA_SYNC = new Identifier(AnimancyMod.MOD_ID, "soul_sync");
    public static final Identifier ANIMANCER_DATA_SYNC = new Identifier(AnimancyMod.MOD_ID, "animancer_sync");
    public static final Identifier REMOVE_SOUL_SHARD = new Identifier(AnimancyMod.MOD_ID, "remove_soul_shard");
    public static final Identifier SYNC_ON_JOIN = new Identifier(AnimancyMod.MOD_ID, "sync_on_join");
    public static final Identifier SYNC_SOUL_RIP_COUNTER = new Identifier(AnimancyMod.MOD_ID, "sync_soul_rip_count");
    public static final Identifier SOUL_RIP = new Identifier(AnimancyMod.MOD_ID, "sync_rip");


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(REMOVE_SOUL_SHARD, RemoveSoulShardC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SYNC_ON_JOIN, SyncOnJoinC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SOUL_RIP, SoulRipC2SPacket::recieve);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SOUL_DATA_SYNC, SyncSoulDataS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ANIMANCER_DATA_SYNC, SyncAnimancerDataS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_SOUL_RIP_COUNTER, SyncSoulRipCounterDataS2CPacket::recieve);
    }
}

package net.spidereye.animancy.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.networking.packet.SyncAnimancerDataS2CPacket;
import net.spidereye.animancy.networking.packet.SyncSoulDataS2CPacket;

public class ModPackets {
    public static final Identifier SOUL_DATA_SYNC = new Identifier(AnimancyMod.MOD_ID, "soul_sync");
    public static final Identifier ANIMANCER_DATA_SYNC = new Identifier(AnimancyMod.MOD_ID, "animancer_sync");


    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SOUL_DATA_SYNC, SyncSoulDataS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(ANIMANCER_DATA_SYNC, SyncAnimancerDataS2CPacket::recieve);
    }
}

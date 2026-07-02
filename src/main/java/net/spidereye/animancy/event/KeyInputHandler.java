package net.spidereye.animancy.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.spidereye.animancy.networking.ModPackets;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ANIMANCY = "key.category.animancy.keybinds";
    public static final String REMOVE_SOUL_SHARD_KEY = "key.animancy.remove_soul_shard_key";
    public static final String SOUL_RIP_KEY = "key.animancy.soul_rip_key";

    public static KeyBinding removeSoulShardKey;
    public static KeyBinding soulRipKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (removeSoulShardKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.REMOVE_SOUL_SHARD, PacketByteBufs.create());
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (soulRipKey.isPressed() && SoulUtil.isAnimancer((IEntityDataSaver) client.player)) {
                ClientPlayNetworking.send(ModPackets.SOUL_RIP, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        removeSoulShardKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                REMOVE_SOUL_SHARD_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_ANIMANCY
        ));

        soulRipKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                SOUL_RIP_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_ANIMANCY
        ));

        registerKeyInputs();
    }
}

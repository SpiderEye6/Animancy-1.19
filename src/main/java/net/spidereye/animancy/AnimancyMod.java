package net.spidereye.animancy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.networking.ModPackets;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.ModLootTableModifiers;
import net.spidereye.animancy.util.SoulData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimancyMod implements ModInitializer {
	public static final String MOD_ID = "animancy";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems();

		ModLootTableModifiers.modifyLootTables();
		ModPackets.registerC2SPackets();

		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			boolean isAnimancer = SoulData.isAnimancer((IEntityDataSaver) oldPlayer);
			double soulSize = SoulData.getSoul((IEntityDataSaver) oldPlayer);
			SoulData.setAnimancer((IEntityDataSaver) newPlayer, isAnimancer);
			SoulData.setSoul((IEntityDataSaver) newPlayer, soulSize);
		});
	}
}

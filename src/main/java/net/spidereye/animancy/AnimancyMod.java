package net.spidereye.animancy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.spidereye.animancy.block.ModBlocks;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.entity.ModEntities;
import net.spidereye.animancy.entity.custom.DraconicRisenEntity;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import net.spidereye.animancy.event.ModEventListeners;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.networking.ModPackets;
import net.spidereye.animancy.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;


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
		ModBlocks.registerModBlocks();

		ModEnchantments.registerEnchantments();

		ModLootTableModifiers.modifyLootTables();
		ModPackets.registerC2SPackets();

		GeckoLib.initialize();
		ModEntities.registerModEntites();

		ServerPlayerEvents.COPY_FROM.register(ModEventListeners::transferSoulDataOnDeath);

		AttackEntityCallback.EVENT.register(ModEventListeners::rendSoulEnchantmentImplementation);

		UseEntityCallback.EVENT.register(ModEventListeners::retrieveSoulShardFromDreg);
		UseEntityCallback.EVENT.register(ModEventListeners::retrieveSoulFromRevenant);
		UseEntityCallback.EVENT.register(ModEventListeners::retrieveSoulFromRisen);

		FabricDefaultAttributeRegistry.register(ModEntities.DREG, DregZombieEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.REVENANT, RevenantEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.DRACONIC_RISEN, DraconicRisenEntity.setAttributes());
	}
}

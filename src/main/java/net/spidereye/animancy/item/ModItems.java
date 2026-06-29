package net.spidereye.animancy.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.item.custom.*;

public class ModItems {
    public static final Item SOUL_SHARD = registerItem("soul_shard",
            new SoulShardItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY)));
    public static final Item SOUL = registerItem("soul",
            new SoulItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).rarity(Rarity.RARE)));
    public static final Item REVENANT_SOUL = registerItem("revenant_soul",
            new RevenantSoulItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).rarity(Rarity.RARE)));
    public static final Item DRAGON_SOUL = registerItem("dragon_soul",
            new DragonSoulItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1).rarity(Rarity.EPIC)));
    public static final Item DRACONIC_RISEN_SOUL = registerItem("draconic_risen_soul",
            new DraconicRisenSoulItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1).rarity(Rarity.EPIC)));

    public static final Item SOUL_STEEL_INGOT = registerItem("soul_steel_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.ANIMANCY).fireproof()));
    public static final Item SOUL_STEEL_SWORD = registerItem("soul_steel_sword",
            new SwordItem(ModToolMaterial.SOUL_STEEL, 3, -2.4f,
                    new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1).fireproof()));
    public static final Item ANIMANTIC_WAR_SCYTHE = registerItem("animantic_war_scythe",
            new AnimanticWarScytheItem(ModToolMaterial.SOUL_STEEL, 5, -3.0f,
                    new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1).fireproof().rarity(Rarity.EPIC)));

    public static final Item SOUL_CHECKER_DEBUG = registerItem("soul_checker_debug",
            new SoulDataCheckerItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1)));
    public static final Item ANIMANCY_CHECKER_DEBUG = registerItem("animancy_checker_debug",
            new AnimancyDataCheckerItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1)));
    public static final Item SOUL_RIP_COUNTER_CHECKER_DEBUG = registerItem("soul_rip_counter_checker_debug",
            new SoulRipCounterDataCheckerItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1)));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AnimancyMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AnimancyMod.LOGGER.debug("Registering Mod Items for " + AnimancyMod.MOD_ID);
    }
}

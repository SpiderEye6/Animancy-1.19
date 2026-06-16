package net.spidereye.animancy.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.item.custom.AnimancyDataCheckerItem;
import net.spidereye.animancy.item.custom.SoulDataCheckerItem;

public class ModItems {
    public static final Item SOUL_SHARD = registerItem("soul_shard",
            new Item(new FabricItemSettings().group(ModItemGroup.ANIMANCY)));

    public static final Item SOUL_CHECKER_DEBUG = registerItem("soul_checker_debug",
            new SoulDataCheckerItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1)));
    public static final Item ANIMANCY_CHECKER_DEBUG = registerItem("animancy_checker_debug",
            new AnimancyDataCheckerItem(new FabricItemSettings().group(ModItemGroup.ANIMANCY).maxCount(1)));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AnimancyMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AnimancyMod.LOGGER.debug("Registering Mod Items for " + AnimancyMod.MOD_ID);
    }
}

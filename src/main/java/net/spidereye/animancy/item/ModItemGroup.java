package net.spidereye.animancy.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;

public class ModItemGroup {
    public static final ItemGroup ANIMANCY = FabricItemGroupBuilder.build(
            new Identifier(AnimancyMod.MOD_ID, "animancy"), () -> new ItemStack(ModItems.SOUL_SHARD));
}

package net.spidereye.animancy.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.enchantment.custom.RendSoulEnchantment;

public class ModEnchantments {
    public static Enchantment REND_SOUL = registerEnchantment("rend_soul",
            new RendSoulEnchantment());

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(AnimancyMod.MOD_ID, name), enchantment);
    }

    public static void registerEnchantments() {
        AnimancyMod.LOGGER.debug("Registering Mod Enchantments for " + AnimancyMod.MOD_ID);
    }
}

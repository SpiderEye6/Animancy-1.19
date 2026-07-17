package net.spidereye.animancy.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class RendSoulEnchantment extends Enchantment {
    public RendSoulEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        ItemStack weapon = user.getMainHandStack();
        if (!SoulUtil.isAnimanticWeapon(weapon)) {
            weapon.decrement(weapon.getCount());
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK,
                    SoundCategory.PLAYERS, 10.0f, 0.0f);
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK,
                    SoundCategory.PLAYERS, 10.0f, 0.0f);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 5, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 5, false, false, false));
            user.setHealth(1.0f);
        }

        super.onTargetDamaged(user, target, level);
    }
}

package net.spidereye.animancy.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class AnimanticWarScytheItem extends HoeItem {
    public AnimanticWarScytheItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && SoulData.isAnimancer((IEntityDataSaver) user)) {
            if (user.getOffHandStack().getItem() == ModItems.SOUL_SHARD &&
                !SoulData.hasEnchantment(user.getMainHandStack(), ModEnchantments.REND_SOUL)) {
                return super.use(world, user, hand);
            }

            double velocity = 0.001D;
            Vec3d lookDir = user.getRotationVector();
            lookDir.normalize().multiply(velocity);

            if (!user.isSneaking()) {
                user.setVelocity(user.getVelocity().add(lookDir));
            } else {
                user.setVelocity(lookDir);
            }

            user.velocityModified = true;
            user.fallDistance = 0.0f;
        }

        return super.use(world, user, hand);
    }
}

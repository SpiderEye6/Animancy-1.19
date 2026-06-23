package net.spidereye.animancy.item.custom;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class SoulShardItem extends Item {
    public SoulShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getHand() == Hand.MAIN_HAND) {
            EntityType.ZOMBIE.spawn(((ServerWorld) context.getWorld()), null, null, null, context.getBlockPos(),
                    SpawnReason.MOB_SUMMONED, true, false); // TODO: Change to a tamed Dreg.
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (hand == Hand.MAIN_HAND) {
                if (!SoulData.isAnimancer((IEntityDataSaver) user)) {
                    SoulData.setAnimancer((IEntityDataSaver) user, true);
                    SoulData.setSoul((IEntityDataSaver) user, 20.0D);
                    // Add Sound Effect?
                    // Add Achievement?
                }

                ItemStack mainHand = user.getMainHandStack();
                if (!user.isSneaking()) {
                    SoulData.addSoul((IEntityDataSaver) user, 1.0D);
                    mainHand.decrement(1);
                } else {
                    int count = mainHand.getCount();
                    SoulData.addSoul((IEntityDataSaver) user, (double) count);
                    mainHand.decrement(count);
                }
            } else {
                if (SoulData.isAnimancer((IEntityDataSaver) user)) {
                    ItemStack mainHand = user.getMainHandStack();
                    ItemStack offHand = user.getOffHandStack();
                    if (!SoulData.hasEnchantment(mainHand, ModEnchantments.REND_SOUL)) {
                        if (mainHand.getItem() == ModItems.SOUL_STEEL_SWORD) {
                            mainHand.addEnchantment(ModEnchantments.REND_SOUL, 1); // TODO: Change to Soul Steel Sword.
                            // Add Sound Effect?
                            offHand.decrement(1);
                        } else if (mainHand.getItem() == ModItems.ANIMANTIC_WAR_SCYTHE) {
                            mainHand.addEnchantment(ModEnchantments.REND_SOUL, 1); // TODO: Change to Animantic War Scythe.
                            // Add Sound Effect?
                            offHand.decrement(1);
                        }
                    }
                }
            }
        }

        return super.use(world, user, hand);
    }
}

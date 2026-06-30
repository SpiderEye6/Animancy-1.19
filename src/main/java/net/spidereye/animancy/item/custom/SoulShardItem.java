package net.spidereye.animancy.item.custom;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.entity.ModEntities;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.RaycastUtil;
import net.spidereye.animancy.util.SoulUtil;

public class SoulShardItem extends Item {
    public SoulShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getHand() == Hand.MAIN_HAND) {
            ModEntities.DREG.spawn(((ServerWorld) context.getWorld()), null, null, null, context.getBlockPos(),
                    SpawnReason.MOB_SUMMONED, true, false).setOwner(context.getPlayer());
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (hand == Hand.MAIN_HAND && !(RaycastUtil.raycastEntity() instanceof DregZombieEntity)) {
                if (!SoulUtil.isAnimancer((IEntityDataSaver) user)) {
                    SoulUtil.setAnimancer((IEntityDataSaver) user, true);
                    SoulUtil.setSoul((IEntityDataSaver) user, 20.0D);
                    // Add Sound Effect?
                    // Add Achievement?
                }

                ItemStack mainHand = user.getMainHandStack();
                if (!user.isSneaking()) {
                    SoulUtil.addSoul((IEntityDataSaver) user, 1.0D);
                    mainHand.decrement(1);
                } else {
                    int count = mainHand.getCount();
                    SoulUtil.addSoul((IEntityDataSaver) user, (double) count);
                    mainHand.decrement(count);
                }
                SoulUtil.playEatSoulSound((ServerWorld) world, user.getBlockPos(), 0.8f, 0.7f);
            } else {
                if (SoulUtil.isAnimancer((IEntityDataSaver) user)) {
                    ItemStack mainHand = user.getMainHandStack();
                    ItemStack offHand = user.getOffHandStack();
                    if (!SoulUtil.hasEnchantment(mainHand, ModEnchantments.REND_SOUL)) {
                        if (mainHand.getItem() == ModItems.SOUL_STEEL_SWORD) {
                            mainHand.addEnchantment(ModEnchantments.REND_SOUL, 1);
                            // Add Sound Effect?
                            offHand.decrement(1);
                        } else if (mainHand.getItem() == ModItems.ANIMANTIC_WAR_SCYTHE) {
                            mainHand.addEnchantment(ModEnchantments.REND_SOUL, 1);
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

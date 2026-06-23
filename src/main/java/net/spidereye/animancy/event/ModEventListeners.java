package net.spidereye.animancy.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class ModEventListeners {
    // Handles transfering of soul data upon death.
    public static void transferSoulDataOnDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        boolean isAnimancer = SoulData.isAnimancer((IEntityDataSaver) oldPlayer);
        double soulSize = SoulData.getSoul((IEntityDataSaver) oldPlayer);
        if (!oldPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            SoulData.setAnimancer((IEntityDataSaver) newPlayer, isAnimancer);
            SoulData.setSoul((IEntityDataSaver) newPlayer, soulSize / 2.0D);
        } else {
            SoulData.setAnimancer((IEntityDataSaver) newPlayer, isAnimancer);
            SoulData.setSoul((IEntityDataSaver) newPlayer, soulSize);
        }
    }

    // Handles implementation of the Rend Soul enchantment because onTargetDamaged() gets called twice for some reason.
    public static ActionResult rendSoulEnchantmentImplementation(PlayerEntity player, World world, Hand hand, Entity entity, HitResult hitResult) {
        if (!world.isClient() && entity instanceof LivingEntity victim && SoulData.isAnimancer((IEntityDataSaver) player)) {
            ItemStack weapon = player.getMainHandStack();
            if (SoulData.hasEnchantment(weapon, ModEnchantments.REND_SOUL)) {
                double soulDamage = 1.0D;
                ItemStack soulPiece;
                if (weapon.getItem() == ModItems.ANIMANTIC_WAR_SCYTHE) {
                    soulDamage = ((MiningToolItem) weapon.getItem()).getAttackDamage();
                    soulPiece = new ItemStack(ModItems.SOUL);
                    SoulData.setSoul(soulPiece, soulDamage);
                } else if (weapon.getItem() == ModItems.SOUL_STEEL_SWORD) {
                    soulPiece = new ItemStack(ModItems.SOUL_SHARD);
                } else {
                    weapon.decrement(weapon.getCount());
                    BlockPos pos = player.getBlockPos();
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1f, 0.5f);
                    world.playSound(null, pos, SoundEvents.AMBIENT_CAVE, SoundCategory.PLAYERS, 1f, 0.5f);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0));
                    player.damage(DamageSource.OUT_OF_WORLD, player.getHealth() - 1.0f);
                    player.sendMessage(Text.translatable("enchantment.animancy.rend_soul.warning")
                            .formatted(Formatting.RED).formatted(Formatting.BOLD), true);
                    return ActionResult.SUCCESS;
                }

                SoulData.addHealthModifier(victim, soulDamage * -1, "rend_soul");
                world.spawnEntity(new ItemEntity(world, victim.getX(), victim.getY(), victim.getZ(), soulPiece));
            }
        }

        return ActionResult.PASS;
    }
}

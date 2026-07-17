package net.spidereye.animancy.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.spidereye.animancy.enchantment.ModEnchantments;
import net.spidereye.animancy.entity.custom.DraconicRisenEntity;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class ModEventListeners {
    private static int MIN_ANIMANCER_TRANSFER = 5;

    // Handles transfering of soul data upon death.
    public static void transferSoulDataOnDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        boolean isAnimancer = SoulUtil.isAnimancer((IEntityDataSaver) oldPlayer);
        double soulSize = SoulUtil.getSoul((IEntityDataSaver) oldPlayer);
        if (!oldPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            double half = soulSize / 2;
            double decrement = soulSize - 20;
            soulSize = Math.max(half, decrement);

            if (soulSize >= MIN_ANIMANCER_TRANSFER) {
                SoulUtil.setAnimancer((IEntityDataSaver) newPlayer, isAnimancer);
            } else {
                SoulUtil.setAnimancer((IEntityDataSaver) newPlayer, false);
            }
        } else {
            SoulUtil.setAnimancer((IEntityDataSaver) newPlayer, isAnimancer);
        }
        SoulUtil.setSoul((IEntityDataSaver) newPlayer, soulSize);
    }

    // Handles implementation of the Rend Soul enchantment because onTargetDamaged() gets called twice for some reason.
    public static ActionResult rendSoulEnchantmentImplementation(PlayerEntity player, World world, Hand hand, Entity entity, HitResult hitResult) {
        if (!world.isClient() && entity instanceof LivingEntity victim && SoulUtil.isAnimancer((IEntityDataSaver) player)) {
            ItemStack weapon = player.getMainHandStack();
            if (SoulUtil.hasEnchantment(weapon, ModEnchantments.REND_SOUL) &&
                    !victim.isUndead() && !(victim instanceof EnderDragonEntity)) {
                double soulDamage = 1.0D;
                ItemStack soulPiece;
                if (weapon.getItem() == ModItems.ANIMANTIC_WAR_SCYTHE) {
                    soulDamage = ((MiningToolItem) weapon.getItem()).getAttackDamage();
                    soulPiece = new ItemStack(ModItems.SOUL);
                    SoulUtil.setSoul(soulPiece, soulDamage);
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


                SoulUtil.addHealthModifier(victim, soulDamage * -1, "rend_soul");
                world.spawnEntity(new ItemEntity(world, victim.getX(), victim.getY(), victim.getZ(), soulPiece));
            }
        }

        return ActionResult.PASS;
    }

    public static ActionResult retrieveSoulShardFromDreg(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (entity instanceof DregZombieEntity dreg) {
                if (dreg.getOwner() == player && SoulUtil.isAnimancer((IEntityDataSaver) player) && dreg.isAlive()) {
                    ItemStack soulShard = new ItemStack(ModItems.SOUL_SHARD);
                    if (player.getInventory().insertStack(soulShard)) {
                        world.spawnEntity(new ItemEntity(world, dreg.getX(), dreg.getY(), dreg.getZ(), soulShard));
                    }
                    dreg.kill();

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    public static ActionResult retrieveSoulFromRevenant(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (entity instanceof RevenantEntity revenant) {
                if (revenant.getOwner() == player && SoulUtil.isAnimancer((IEntityDataSaver) player) && revenant.isAlive()) {
                    ItemStack revenantSoul = new ItemStack(ModItems.REVENANT_SOUL);
                    SoulUtil.setSoul(revenantSoul, revenant.getMaxHealth());
                    if (player.getInventory().insertStack(revenantSoul)) {
                        world.spawnEntity(new ItemEntity(world, revenant.getX(), revenant.getY(), revenant.getZ(), revenantSoul));
                    }
                    revenant.kill();

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    public static ActionResult retrieveSoulFromRisen(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (entity instanceof DraconicRisenEntity risen) {
                if (risen.getOwner() == player && SoulUtil.isAnimancer((IEntityDataSaver) player) && risen.isAlive()) {
                    ItemStack risenSoul = new ItemStack(ModItems.DRACONIC_RISEN_SOUL);
                    if (player.getInventory().insertStack(risenSoul)) {
                        world.spawnEntity(new ItemEntity(world, risen.getX(), risen.getY(), risen.getZ(), risenSoul));
                    }
                    risen.kill();

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}

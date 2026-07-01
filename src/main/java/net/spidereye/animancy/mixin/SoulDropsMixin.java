package net.spidereye.animancy.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SoulDropsMixin {
    @Shadow
    public abstract boolean isUndead();

    @Shadow
    public abstract float getMaxHealth();

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    protected void injectOnKilledBy(LivingEntity adversary, CallbackInfo ci) {
        if (adversary != null) {
            if ((Object) this instanceof LivingEntity entity) {
                World world = adversary.getWorld();
                if (!world.isClient() && SoulUtil.isAnimancer((IEntityDataSaver) adversary)) {
                    if (SoulUtil.isAnimancer((IEntityDataSaver) adversary) || holdingAnimanticWeapon(adversary)) {
                        if (this.isUndead() && !(entity instanceof RevenantEntity)) {
                            handleSoulShardSpawns(adversary);
                        } else if (entity instanceof RevenantEntity) {
                            ItemStack soul = SoulUtil.makeRevenantSoulItemVariant(this.getMaxHealth());
                            BlockPos pos = entity.getBlockPos();
                            ItemEntity soulShard = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), soul);
                            world.spawnEntity(soulShard);
                        } else {
                            ItemStack soul = SoulUtil.makeSoulItemVariant(this.getMaxHealth());
                            BlockPos pos = entity.getBlockPos();
                            ItemEntity soulShard = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), soul);
                            world.spawnEntity(soulShard);
                        }
                    }
                }
            }
        }
    }

    private void handleSoulShardSpawns(LivingEntity adversary) {
        if ((Object) this instanceof LivingEntity entity) {
            World world = adversary.getWorld();
            BlockPos pos = entity.getBlockPos();
            ItemEntity soulShard = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SOUL_SHARD));
            world.spawnEntity(soulShard);
        }
    }

    private boolean holdingAnimanticWeapon(LivingEntity adversary) {
        ItemStack mainHand = adversary.getMainHandStack();
        return SoulUtil.isAnimanticWeapon(mainHand);
    }
}

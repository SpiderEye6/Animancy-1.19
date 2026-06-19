package net.spidereye.animancy.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SoulDropsMixin implements Nameable, EntityLike, CommandOutput {
    @Shadow
    public abstract boolean isUndead();

    @Shadow
    public abstract float getMaxHealth();

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    protected void injectOnKilledBy(LivingEntity adversary, CallbackInfo ci) {
        if (adversary != null) {
            World world = adversary.getWorld();
            if (!world.isClient() && adversary instanceof PlayerEntity) {
                if (SoulData.isAnimancer((IEntityDataSaver) adversary) || holdingAnimanticWeapon(adversary)) {
                    if (this.isUndead()) {
                        handleSoulShardSpawns(adversary);
                    } else {
                        handleSoulSpawns(adversary);
                    }
                }
            }
        }
    }

    private void handleSoulShardSpawns(LivingEntity adversary) {
        World world = adversary.getWorld();
        BlockPos pos = this.getBlockPos();
        ItemEntity soulShard = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SOUL_SHARD));
        world.spawnEntity(soulShard);
    }

    private boolean holdingAnimanticWeapon(LivingEntity adversary) {
        ItemStack mainHand = adversary.getMainHandStack();
        return (mainHand.getItem() == Items.NETHERITE_SWORD) ||
                (mainHand.getItem() == Items.NETHERITE_HOE); // TODO: Change for animantic weapons.
    }

    private void handleSoulSpawns(LivingEntity adversary) {
        World world = adversary.getWorld();
        BlockPos pos = this.getBlockPos();
        ItemStack itemStack = new ItemStack(ModItems.SOUL);
        itemStack.getOrCreateNbt().putDouble("size", (double) this.getMaxHealth());
        ItemEntity soul = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        world.spawnEntity(soul);
    }
}

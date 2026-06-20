package net.spidereye.animancy.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonSoulItem extends Item {
    public DragonSoulItem(Settings settings) {
        super(settings);
    }

    private static double soulSize = 200.0D;

    public static double getSoulSize() {
        return soulSize;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (SoulData.isAnimancer((IEntityDataSaver) user)) {
                ItemStack mainHand = user.getMainHandStack();
                ItemStack offHand = user.getOffHandStack();

                if (offHand.getItem() != ModItems.SOUL_SHARD) {
                    SoulData.addSoul((IEntityDataSaver) user, soulSize);
                    user.heal((float) soulSize/ 100.0f);
                    mainHand.decrement(1);
                } else {
                    ItemStack revenantSoul = new ItemStack(ModItems.REVENANT_SOUL);
                    revenantSoul.getOrCreateNbt().putDouble("size", soulSize);
                    if (!user.getInventory().insertStack(revenantSoul)) {
                        world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), revenantSoul));
                    }
                    mainHand.decrement(1);
                    offHand.decrement(1);
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.tooltip.animancy.dragon_soul"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}

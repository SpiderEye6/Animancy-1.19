package net.spidereye.animancy.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
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
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DraconicRisenSoulItem extends Item {
    public DraconicRisenSoulItem(Settings settings) {
        super(settings);
    }

    private static double soulSize = 200.0D;

    public static double getSoulSize() {
        return soulSize;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getHand() == Hand.MAIN_HAND) {
            EntityType.ENDER_DRAGON.spawn(((ServerWorld) context.getWorld()), null, null, null, context.getBlockPos(),
                    SpawnReason.MOB_SUMMONED, true, false); // TODO: Change to a tamed Draconic Risen.
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (SoulData.isAnimancer((IEntityDataSaver) user)) {
                SoulData.addSoul((IEntityDataSaver) user, soulSize);
                user.heal((float) soulSize/ 100.0f);
                user.getMainHandStack().decrement(1);
            }
            SoulData.playEatSoulSound((ServerWorld) world, user.getBlockPos(), 0.8f, 0.7f);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.tooltip.animancy.draconic_risen"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}

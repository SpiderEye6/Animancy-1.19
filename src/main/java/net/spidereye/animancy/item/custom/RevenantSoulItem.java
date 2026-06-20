package net.spidereye.animancy.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
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

public class RevenantSoulItem extends Item {
    public RevenantSoulItem(Settings settings) {
        super(settings);
    }

    private void writeNbt(double size, NbtCompound nbt) {
        nbt.putDouble("size", size);
    }

    public double getSoulSize(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getNbt();
        if (nbt != null) {
            return nbt.getDouble("size");
        }

        return 20.0D;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getHand() == Hand.MAIN_HAND) {
            EntityType.ZOMBIE_VILLAGER.spawn(((ServerWorld) context.getWorld()), null, null, null, context.getBlockPos(),
                    SpawnReason.MOB_SUMMONED, true, false); // TODO: Change to a tamed Revenant.
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND && SoulData.isAnimancer((IEntityDataSaver) user)) {
            ItemStack mainHand = user.getMainHandStack();
            double size = getSoulSize(mainHand);

            if (!user.isSneaking()) {
                SoulData.addSoul((IEntityDataSaver) user, size);
                user.heal((float) size/ 100.0f);
                mainHand.decrement(1);
            } else {
                SoulData.addSoul((IEntityDataSaver) user, size * mainHand.getCount());
                user.heal((float) size/ 100.0f * mainHand.getCount());
                mainHand.decrement(mainHand.getCount());
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Soul size: " + getSoulSize(stack)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}

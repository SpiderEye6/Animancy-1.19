package net.spidereye.animancy.item.custom;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulItem extends Item {
    public SoulItem(Settings settings) {
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (SoulData.isAnimancer((IEntityDataSaver) user) &&
                    user.getOffHandStack().getItem() != ModItems.SOUL_SHARD) {

                ItemStack mainHand = user.getMainHandStack();
                double size = getSoulSize(mainHand);
                if (size < 0) {
                    user.sendMessage(Text.literal("How did you get a negative soul??"));
                    return super.use(world, user, hand);
                }

                if (!user.isSneaking()) {
                    SoulData.addSoul((IEntityDataSaver) user, size);
                    user.heal((float) size/ 100.0f);
                    mainHand.decrement(1);
                } else {
                    SoulData.addSoul((IEntityDataSaver) user, size * mainHand.getCount());
                    user.heal((float) size/ 100.0f * mainHand.getCount());
                    mainHand.decrement(mainHand.getCount());
                }
            } // TODO: Add turn into revenant soul
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Soul size: " + getSoulSize(stack)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}

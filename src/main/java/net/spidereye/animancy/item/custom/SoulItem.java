package net.spidereye.animancy.item.custom;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
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
            if (SoulData.isAnimancer((IEntityDataSaver) user)) {
                ItemStack mainHand = user.getMainHandStack();
                ItemStack offHand = user.getOffHandStack();

                if (offHand.getItem() != ModItems.SOUL_SHARD) {
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
                } else {
                    ItemStack revenantSoul = new ItemStack(ModItems.REVENANT_SOUL);
                    SoulData.setSoul(revenantSoul, SoulData.getSoul(mainHand));
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
        tooltip.add(Text.of("Soul size: " + getSoulSize(stack)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}

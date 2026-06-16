package net.spidereye.animancy.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class SoulDataCheckerItem extends Item {
    public SoulDataCheckerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (hand == Hand.OFF_HAND) {
                SoulData.setSoul((IEntityDataSaver) user, 20);
            } else if (!user.isSneaking()) {
                SoulData.addSoul((IEntityDataSaver) user, 1);
            } else {
                SoulData.removeSoul((IEntityDataSaver) user, 1);
            }

            user.sendMessage(Text.literal("Soul: " + SoulData.getSoul((IEntityDataSaver) user)));
        }

        return super.use(world, user, hand);
    }
}

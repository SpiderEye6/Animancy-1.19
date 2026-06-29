package net.spidereye.animancy.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class SoulRipCounterDataCheckerItem extends Item {
    public SoulRipCounterDataCheckerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (hand == Hand.OFF_HAND) {
                SoulUtil.setSoulRipCounter((IEntityDataSaver) user, 0);
            } else if (!user.isSneaking()) {
                SoulUtil.addSoulRipCounter((IEntityDataSaver) user, 5);
            } else {
                SoulUtil.removeSoulRipCounter((IEntityDataSaver) user, 1);
            }
        }

        return super.use(world, user, hand);
    }
}

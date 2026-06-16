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

public class AnimancyDataCheckerItem extends Item {
    public AnimancyDataCheckerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (hand == Hand.MAIN_HAND) {
                SoulData.setAnimancer((IEntityDataSaver) user, !SoulData.isAnimancer((IEntityDataSaver) user));
            } else {
                user.sendMessage(Text.literal("Animancer: " + SoulData.isAnimancer((IEntityDataSaver) user)));
            }
        }

        return super.use(world, user, hand);
    }
}

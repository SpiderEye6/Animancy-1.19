package net.spidereye.animancy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class ModLivingEntityTickMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void injectTick(CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity livingEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!livingEntity.getWorld().isClient() && client.player != null) {
                if (SoulUtil.getSoulRipCounter((IEntityDataSaver) livingEntity) > 0.1) {
                    SoulUtil.emitSoulGroan(livingEntity, 1.0f);
                }
                double rip = Math.max(livingEntity.getMaxHealth(), SoulUtil.getSoul((IEntityDataSaver) livingEntity));
                SoulUtil.removeSoulRipCounter((IEntityDataSaver) livingEntity, rip / 100);
            }
        }
    }
}

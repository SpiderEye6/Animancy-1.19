package net.spidereye.animancy.entity.client.model;

import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DregZombieModel extends AnimatedGeoModel<DregZombieEntity> {
    @Override
    public Identifier getModelResource(DregZombieEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "geo/dreg_zombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(DregZombieEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/dreg_zombie_texture.png");
    }

    @Override
    public Identifier getAnimationResource(DregZombieEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "animations/dreg_zombie.animation.json");
    }
}

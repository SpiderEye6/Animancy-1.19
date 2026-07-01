package net.spidereye.animancy.entity.client.model;

import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RevenantModel extends AnimatedGeoModel<RevenantEntity> {
    @Override
    public Identifier getModelResource(RevenantEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "geo/revenant.geo.json");
    }

    @Override
    public Identifier getTextureResource(RevenantEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/revenant_texture.png");
    }

    @Override
    public Identifier getAnimationResource(RevenantEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "animations/revenant.animation.json");
    }
}

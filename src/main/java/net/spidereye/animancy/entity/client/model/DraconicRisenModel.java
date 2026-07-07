package net.spidereye.animancy.entity.client.model;

import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.custom.DraconicRisenEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DraconicRisenModel extends AnimatedGeoModel<DraconicRisenEntity> {
    @Override
    public Identifier getModelResource(DraconicRisenEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "geo/draconic_risen.geo.json");
    }

    @Override
    public Identifier getTextureResource(DraconicRisenEntity object) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/draconic_risen_texture.png");
    }

    @Override
    public Identifier getAnimationResource(DraconicRisenEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "animations/draconic_risen.animation.json");
    }
}

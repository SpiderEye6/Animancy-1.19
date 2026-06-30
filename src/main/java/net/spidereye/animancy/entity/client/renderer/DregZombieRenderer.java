package net.spidereye.animancy.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.client.model.DregZombieModel;
import net.spidereye.animancy.entity.custom.DregZombieEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DregZombieRenderer extends GeoEntityRenderer<DregZombieEntity> {
    public DregZombieRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DregZombieModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(DregZombieEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/dreg_zombie_texture.png");
    }

    @Override
    public RenderLayer getRenderType(DregZombieEntity animatable, float partialTick, MatrixStack stack,
                                     @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                                     int packedLight, Identifier texture) {
        // Can fuck with shit like render scale because we have matrices.

        return super.getRenderType(animatable, partialTick, stack, bufferSource, buffer, packedLight, texture);
    }
}

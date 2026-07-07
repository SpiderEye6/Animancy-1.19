package net.spidereye.animancy.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.client.model.DraconicRisenModel;
import net.spidereye.animancy.entity.client.model.RevenantModel;
import net.spidereye.animancy.entity.custom.DraconicRisenEntity;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DraconicRisenRenderer extends GeoEntityRenderer<DraconicRisenEntity> {
    public DraconicRisenRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DraconicRisenModel());
        this.shadowRadius = 4.0f;
    }

    @Override
    public Identifier getTextureResource(DraconicRisenEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/draconic_risen_texture.png");
    }

    @Override
    public RenderLayer getRenderType(DraconicRisenEntity animatable, float partialTick, MatrixStack stack,
                                     @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                                     int packedLight, Identifier texture) {
        // Can fuck with shit like render scale because we have matrices.
        stack.scale(10.0f, 1.2f, 10.0f);

        return super.getRenderType(animatable, partialTick, stack, bufferSource, buffer, packedLight, texture);
    }
}

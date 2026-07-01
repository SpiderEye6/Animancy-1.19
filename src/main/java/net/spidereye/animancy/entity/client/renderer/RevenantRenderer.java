package net.spidereye.animancy.entity.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.client.model.RevenantModel;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RevenantRenderer extends GeoEntityRenderer<RevenantEntity> {
    public RevenantRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RevenantModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public Identifier getTextureResource(RevenantEntity animatable) {
        return new Identifier(AnimancyMod.MOD_ID, "textures/entity/revenant_texture.png");
    }

    @Override
    public RenderLayer getRenderType(RevenantEntity animatable, float partialTick, MatrixStack stack,
                                     @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                                     int packedLight, Identifier texture) {
        // Can fuck with shit like render scale because we have matrices.
        stack.scale(1.2f, 1.2f, 1.2f);

        return super.getRenderType(animatable, partialTick, stack, bufferSource, buffer, packedLight, texture);
    }
}

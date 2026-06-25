package net.spidereye.animancy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulData;

public class AnimancyHudOverlay implements HudRenderCallback {
    private static final Identifier SOUL_RIP_SOUL_SHARD_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/animancer/soul_rip_soul_shard_element.png");
    private static final Identifier SOUL_RIP_SOUL_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/animancer/soul_rip_soul_element.png");
    private static final Identifier SOUL_RIP_DRAGON_SOUL_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/animancer/soul_rip_dragon_soul_element.png");
    private static final Identifier SOUL_SIZE_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/animancer/soul_size_element.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

        renderSoulSizeElement(matrixStack, tickDelta, x, y);
    }

    private void renderSoulSizeElement(MatrixStack matrixStack, float tickDelta, int x, int y) {
        PlayerEntity player = MinecraftClient.getInstance().player;;
        if (player.isSpectator() || !SoulData.isAnimancer((IEntityDataSaver) player)) {
            return;
        }

        String soulSize = Integer.toString((int) Math.floor(SoulData.getSoul((IEntityDataSaver) player)));
        int color = 0xFFFFFFFF;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, SOUL_SIZE_ELEMENT);
        DrawableHelper.drawTexture(matrixStack, x + 394, y - 54, 0, 0, 16, 16,
                16, 16);
        DrawableHelper.drawCenteredText(matrixStack, MinecraftClient.getInstance().textRenderer, soulSize, x + 406, y - 37, color);
    }



    private Entity raycastEntity(double maxDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.cameraEntity.raycast(maxDistance, 1.0f, false);

        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            return entityHit.getEntity();
        } else {
            return null;
        }
    }
}
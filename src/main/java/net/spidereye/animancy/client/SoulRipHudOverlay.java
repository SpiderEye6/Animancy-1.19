package net.spidereye.animancy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.SoulUtil;

public class SoulRipHudOverlay implements HudRenderCallback {
    private static final Identifier SOUL_RIP_OVERLAY_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/soul_rip/soul_rip_overlay_element.png");

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

        PlayerEntity player = client.player;
        float alpha = SoulUtil.getSoulRipPercentage((IEntityDataSaver) player);

        if (SoulUtil.getSoulRipCounter((IEntityDataSaver) player) <= 0.1) {
            return;
        }

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 0.0f, alpha);
        RenderSystem.setShaderTexture(0, SOUL_RIP_OVERLAY_ELEMENT);
        DrawableHelper.drawTexture(matrixStack, 0, 0, 0, 0, x * 2, y,
                32, 32);
    }
}

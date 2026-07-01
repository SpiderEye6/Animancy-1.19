package net.spidereye.animancy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spidereye.animancy.AnimancyMod;
import net.spidereye.animancy.entity.custom.RevenantEntity;
import net.spidereye.animancy.item.ModItems;
import net.spidereye.animancy.util.IEntityDataSaver;
import net.spidereye.animancy.util.RaycastUtil;
import net.spidereye.animancy.util.SoulUtil;

public class AnimancerHudOverlay implements HudRenderCallback {
    private static final Identifier SOUL_RIP_SOUL_SHARD_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/animancer/soul_rip_soul_shard_element.png");
    private static final Identifier SOUL_RIP_SOUL_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/animancer/soul_rip_soul_element.png");
    private static final Identifier SOUL_RIP_REVENANT_SOUL_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/animancer/soul_rip_revenant_soul_element.png");
    private static final Identifier SOUL_RIP_DRAGON_SOUL_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/animancer/soul_rip_dragon_soul_element.png");
    private static final Identifier SOUL_SIZE_ELEMENT = new Identifier(AnimancyMod.MOD_ID,
            "textures/gui/animancer/soul_size_element.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player.isSpectator() || !SoulUtil.isAnimancer((IEntityDataSaver) player)) {
            return;
        }

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
        renderCanSoulRip(matrixStack, tickDelta, x, y);
    }

    private void renderSoulSizeElement(MatrixStack matrixStack, float tickDelta, int x, int y) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        String soulSize = Integer.toString((int) Math.floor(SoulUtil.getSoul((IEntityDataSaver) player)));
        int color = 0xFFFFFFFF;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, SOUL_SIZE_ELEMENT);
        DrawableHelper.drawTexture(matrixStack, x + 394, y - 54, 0, 0, 16, 16,
                16, 16);
        DrawableHelper.drawCenteredText(matrixStack, MinecraftClient.getInstance().textRenderer, soulSize, x + 406, y - 37, color);
    }

    private void renderCanSoulRip(MatrixStack matrixStack, float tickDelta, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        double maxDistance = SoulUtil.soulRange((IEntityDataSaver) player);
        Entity entity = RaycastUtil.raycastEntity(client.cameraEntity, maxDistance, 1.0f);
        int color = 0xFFFFFFFF;
        if (entity == null) {
            return;
        }
        if (!(entity instanceof LivingEntity victim)) {
            return;
        }

        double victimSoulSize = victim.getMaxHealth();
        Item soulType;
        if (victim.isUndead() && !(victim instanceof RevenantEntity)) {
            soulType = (new ItemStack(ModItems.SOUL_SHARD)).getItem();
        } else if (victim instanceof RevenantEntity) {
            soulType = SoulUtil.makeRevenantSoulItemVariant(victimSoulSize).getItem();
        } else {
            soulType = SoulUtil.makeSoulItemVariant(victimSoulSize).getItem();
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (soulType == ModItems.SOUL_SHARD) {
            RenderSystem.setShaderTexture(0, SOUL_RIP_SOUL_SHARD_ELEMENT);
        } else if (soulType == ModItems.SOUL) {
            RenderSystem.setShaderTexture(0, SOUL_RIP_SOUL_ELEMENT);
        } else if (soulType == ModItems.DRACONIC_RISEN_SOUL) {
            RenderSystem.setShaderTexture(0, SOUL_RIP_DRAGON_SOUL_ELEMENT);
        } else if (soulType == ModItems.REVENANT_SOUL) {
            RenderSystem.setShaderTexture(0, SOUL_RIP_REVENANT_SOUL_ELEMENT);
        }
        DrawableHelper.drawTexture(matrixStack, x - 8, y - 237, 0, 0, 16, 16,
                16, 16);

        if (soulType == ModItems.SOUL || soulType == ModItems.REVENANT_SOUL) {
            DrawableHelper.drawCenteredText(matrixStack, MinecraftClient.getInstance().textRenderer,
                    Integer.toString((int) victimSoulSize), x - 6, y - 237, color);
        }
    }}
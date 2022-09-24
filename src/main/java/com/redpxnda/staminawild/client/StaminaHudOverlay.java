package com.redpxnda.staminawild.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.redpxnda.staminawild.StaminaWild;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StaminaHudOverlay {
    private static final ResourceLocation FILLED_STAMINA = new ResourceLocation("staminawild", "textures/stamina/filled.png");
    private static final ResourceLocation EMPTY_STAMINA = new ResourceLocation("staminawild", "textures/stamina/empty.png");
    private static final ResourceLocation HALF_STAMINA = new ResourceLocation("staminawild", "textures/stamina/half.png");
    private static boolean isMounted;

    @SubscribeEvent
    public void postOverlay(RenderGameOverlayEvent.PreLayer event) {
        if (event.getOverlay() == ForgeIngameGui.MOUNT_HEALTH_ELEMENT) {
            System.out.println("HORSE!");
            isMounted = true;
        }
    }

    public static final IIngameOverlay HUD_STAMINA = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;
        if (!isMounted) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            double staminaFull = ClientStaminaData.getPlayerStamina() / 100F;
            double staminaEmpty = Math.round(staminaFull * 2.0) / 2.0;
            staminaFull = ClientStaminaData.getPlayerStamina() / 100F;
            int simpleStamina = ClientStaminaData.getPlayerStamina() / 100;
            for (double i = 0; i < staminaFull; i++) {
                RenderSystem.setShaderTexture(0, FILLED_STAMINA);
                int i2 = (int) i;
                GuiComponent.blit(poseStack, x - 94 + (i2 * 8), y - 54, 0, 0, 9, 9, 9, 9);
            }
            for (double i = staminaFull; i < 10; i++) {
                RenderSystem.setShaderTexture(0, EMPTY_STAMINA);
                int i2 = (int) i;
                GuiComponent.blit(poseStack, x - 94 + (i2 * 8), y - 54, 0, 0, 9, 9, 9, 9);
            }
            if (Math.abs(staminaFull - simpleStamina) > 0.4) {
                RenderSystem.setShaderTexture(0, HALF_STAMINA);
                int iFull = (int) staminaFull;
                GuiComponent.blit(poseStack, x - 94 + (iFull * 8), y - 54, 0, 0, 9, 9, 9, 9);
            }
        }
    });
}

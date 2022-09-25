package com.redpxnda.staminawild.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.redpxnda.staminawild.StaminaWild;
import com.redpxnda.staminawild.config.ClientConfig;
import com.redpxnda.staminawild.config.CommonConfig;
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
import org.lwjgl.system.CallbackI;

import java.lang.reflect.Array;

public class StaminaHudOverlay {
    private static final ResourceLocation FILLED_STAMINA = new ResourceLocation("staminawild", "textures/stamina/filled.png");
    private static final ResourceLocation EMPTY_STAMINA = new ResourceLocation("staminawild", "textures/stamina/empty.png");
    private static final ResourceLocation HALF_STAMINA = new ResourceLocation("staminawild", "textures/stamina/half.png");
    private static final ResourceLocation EMPTY_BAR_H = new ResourceLocation("staminawild", "textures/stamina/bar/empty_h.png");
    private static final ResourceLocation FULL_BAR_H = new ResourceLocation("staminawild", "textures/stamina/bar/full_h.png");
    private static final ResourceLocation EMPTY_BAR_V = new ResourceLocation("staminawild", "textures/stamina/bar/empty_v.png");
    private static final ResourceLocation FULL_BAR_V = new ResourceLocation("staminawild", "textures/stamina/bar/full_v.png");
    private static final ResourceLocation[] EGG_STAMINA = new ResourceLocation[23];
    private static final PoseStack RENDER_REVERTED = new PoseStack();

    static {
        EGG_STAMINA[0] = new ResourceLocation("staminawild", "textures/stamina/eggs/00.png");
        for (int i = 1; i <= 22; i++) {
            EGG_STAMINA[i] = new ResourceLocation("staminawild", "textures/stamina/eggs/0" + i + ".png");
        }
    }
    public static final IIngameOverlay HUD_STAMINA = ((gui, poseStack, partialTick, width, height) -> {
        if (ClientConfig.RENDER_MODE.get().equals("Segments") || ClientConfig.RENDER_MODE.get().equals("s")) {
            int x = width / 2;
            int y = height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int simpleStamina = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / (ClientConfig.SEGMENTS.get()*22)) - 1;
            for (int i = 1; i <= ClientConfig.SEGMENTS.get(); i++) {
                int i2 = i;
                if (i*22 >= simpleStamina) {
                    RenderSystem.setShaderTexture(0, EGG_STAMINA[1]);
                    poseStack.translate(0,0,-5);
                    GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get() + (i * 23)-23, y - ClientConfig.POSITION_Y.get(), 0, 0, 22, 9, 22, 9);
                }
                if (i*22 <= simpleStamina) {
                    RenderSystem.setShaderTexture(0, EGG_STAMINA[22]);
                    GuiComponent.blit(poseStack, x-ClientConfig.POSITION_X.get()+(i*23)-23, y - ClientConfig.POSITION_Y.get(), 0, 0, 22, 9, 22, 9);
                }
                if (((i+1)*22 > simpleStamina && i*22 < simpleStamina) || (i*22 > simpleStamina && i<2)) {
                    int progress = simpleStamina - (i*22);
                    if (simpleStamina < 22) {
                        progress+=22;
                        i2--;
                    }
                    RenderSystem.setShaderTexture(0, EGG_STAMINA[progress]);
                    poseStack.translate(0,0,5);
                    GuiComponent.blit(poseStack, x-ClientConfig.POSITION_X.get()+(i2*23), y - ClientConfig.POSITION_Y.get(), 0, 0, 22, 9, 22, 9);
                }

            }
        } else if (ClientConfig.RENDER_MODE.get().equals("Horizontal Bar") || ClientConfig.RENDER_MODE.get().equals("hb")) {
            int x = width / 2;
            int y = height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_BAR_H);
            poseStack.translate(0,0,-5);
            GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get(), y - ClientConfig.POSITION_Y.get(), 0, 0, ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get());
            RenderSystem.setShaderTexture(0, FULL_BAR_H);
            int divideAmount = CommonConfig.MAX_STAMINA.get()/ClientConfig.BAR_LENGTH.get();
            GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get(), y - ClientConfig.POSITION_Y.get(), 0, 0, ClientStaminaData.getPlayerStamina()/divideAmount, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get());
        } else if (ClientConfig.RENDER_MODE.get().equals("Vertical Bar") || ClientConfig.RENDER_MODE.get().equals("vb")) {
            int x = width / 2;
            int y = height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_BAR_V);
            poseStack.translate(0,0,-5);
            GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get(), y - ClientConfig.POSITION_Y.get(), 0, 0, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get());
            poseStack.translate(0,0,5);
            RenderSystem.setShaderTexture(0, FULL_BAR_V);
            int divideAmount = CommonConfig.MAX_STAMINA.get()/ClientConfig.BAR_LENGTH.get();
            GuiComponent.blit(RENDER_REVERTED, x - ClientConfig.POSITION_X.get(), y - ClientConfig.POSITION_Y.get(), 0, 0, ClientConfig.BAR_WIDTH.get(), ClientStaminaData.getPlayerStamina()/divideAmount, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get());
        } else {
            int x = width / 2;
            int y = height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            double staminaFull = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10F);
            double staminaEmpty = Math.round(staminaFull * 2.0) / 2.0;
            staminaFull = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10F);
            int simpleStamina = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10);
            for (double i = 0; i < staminaFull; i++) {
                RenderSystem.setShaderTexture(0, FILLED_STAMINA);
                int i2 = (int) i;
                GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get() + (i2 * 8), y - ClientConfig.POSITION_Y.get(), 0, 0, 9, 9, 9, 9);
            }
            for (double i = staminaFull; i < 10; i++) {
                RenderSystem.setShaderTexture(0, EMPTY_STAMINA);
                int i2 = (int) i;
                GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get() + (i2 * 8), y - ClientConfig.POSITION_Y.get(), 0, 0, 9, 9, 9, 9);
            }
            if (Math.abs(staminaFull - simpleStamina) > 0.4) {
                RenderSystem.setShaderTexture(0, HALF_STAMINA);
                int iFull = (int) staminaFull;
                GuiComponent.blit(poseStack, x - ClientConfig.POSITION_X.get() + (iFull * 8), y - ClientConfig.POSITION_Y.get(), 0, 0, 9, 9, 9, 9);
            }
        }
    });
}

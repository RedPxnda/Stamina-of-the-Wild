package com.redpxnda.staminawild.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.redpxnda.staminawild.config.ClientConfig;
import com.redpxnda.staminawild.config.CommonConfig;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.lang.reflect.Array;

public class StaminaHudOverlay {
    private static final ResourceLocation FILLED_STAMINA = new ResourceLocation("staminawild", "textures/stamina/filled.png");
    private static final ResourceLocation EMPTY_STAMINA = new ResourceLocation("staminawild", "textures/stamina/empty.png");
    private static final ResourceLocation HALF_STAMINA = new ResourceLocation("staminawild", "textures/stamina/half.png");
    private static final ResourceLocation EMPTY_BAR_H = new ResourceLocation("staminawild", "textures/stamina/bar/empty_h.png");
    private static final ResourceLocation FULL_BAR_H = new ResourceLocation("staminawild", "textures/stamina/bar/full_h.png");
    private static final ResourceLocation EMPTY_BAR_V = new ResourceLocation("staminawild", "textures/stamina/bar/empty_v.png");
    private static final ResourceLocation FULL_BAR_V = new ResourceLocation("staminawild", "textures/stamina/bar/full_v.png");
    private static final ResourceLocation EGG_STAMINA = new ResourceLocation("staminawild", "textures/stamina/eggs.png");


    //main method for rendering
    public static final IIngameOverlay HUD_STAMINA = ((gui, poseStack, partialTick, width, height) -> {
        int x;
        int y;
        int mode = 1;
        switch (ClientConfig.RENDER_MODE.get()) {
            case "s", "Segments", "eggs", "Seggments" -> {
                switch (ClientConfig.POSITION_PRESET.get()) {
                    case "bottom_right" -> {
                        x = width-25;
                        y = height-15;
                        mode = -1;
                    }
                    case "bottom_left" -> {
                        x = 3;
                        y = height-15;
                    }
                    case "top_right" -> {
                        x = width-25;
                        y = 15;
                        mode = -1;
                    }
                    case "top_left" -> {
                        x = 3;
                        y = 15;
                    }
                    default -> {
                        x = width / 2;
                        y = height;
                    }
                }
                x+=ClientConfig.POSITION_X.get();
                y+=ClientConfig.POSITION_Y.get();
                eggRendering(gui, poseStack, partialTick, x, y, mode);
            }
            case "hb", "Horizontal Bar", "bar" -> {
                switch (ClientConfig.POSITION_PRESET.get()) {
                    case "bottom_right" -> {
                        x = width-3-ClientConfig.BAR_LENGTH.get();
                        y = height-25;
                    }
                    case "bottom_left" -> {
                        x = 3;
                        y = height-25;
                    }
                    case "top_right" -> {
                        x = width-3-ClientConfig.BAR_LENGTH.get();
                        y = 5;
                    }
                    case "top_left" -> {
                        x = 3;
                        y = 5;
                    }
                    default -> {
                        x = width / 2;
                        y = height;
                    }
                }
                x+=ClientConfig.POSITION_X.get();
                y+=ClientConfig.POSITION_Y.get();
                horizontalBarRendering(gui, poseStack, partialTick, x, y);
            }
            case "vb", "Vertical Bar"-> {
                switch (ClientConfig.POSITION_PRESET.get()) {
                    case "bottom_right" -> {
                        x = (int) Math.round(width*1.8);
                        y = height-5;
                    }
                    case "bottom_left" -> {
                        x = -30;
                        y = height-5;
                    }
                    case "top_right" -> {
                        x = (int) Math.round(width*1.8);
                        y = ClientConfig.BAR_LENGTH.get() + 5;
                    }
                    case "top_left" -> {
                        x = -30;
                        y = ClientConfig.BAR_LENGTH.get() + 5;
                    }
                    default -> {
                        x = width / 2;
                        y = height;
                    }
                }
                x+=ClientConfig.POSITION_X.get();
                y+=ClientConfig.POSITION_Y.get();
                verticalBarRendering(gui, poseStack, partialTick, x, y);
            }
            default -> {
                switch (ClientConfig.POSITION_PRESET.get()) {
                    case "bottom_right" -> {
                        x = width-25;
                        y = height-15;
                        mode = -1;
                    }
                    case "bottom_left" -> {
                        x = 3;
                        y = height-15;
                    }
                    case "top_right" -> {
                        x = width-25;
                        y = 15;
                        mode = -1;
                    }
                    case "top_left" -> {
                        x = 3;
                        y = 15;
                    }
                    default -> {
                        x = width / 2;
                        y = height;
                    }
                }
                lightningBoltRendering(gui, poseStack, partialTick, x, y, mode);
            }
        }
    });
    private static void eggRendering(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int x, int y, int mode) {
        // V to make eggs anchor to the right on right presets
        x-=23*mode;
        float stamina = (ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / (ClientConfig.SEGMENTS.get() * 22f)) - 1) / 22f;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        for (int segment = 1; segment <= ClientConfig.SEGMENTS.get(); segment++) {
            int textureX;
            if (segment <= stamina) {
                textureX = 21*22;
            }
            else if (segment > stamina + 1) {
                textureX = 0;
            }
            else {
                textureX = (int)(Math.ceil((stamina - (int)stamina) * 22)) * 22 - 22;
                if(ClientStaminaData.getPlayerStamina() == CommonConfig.MAX_STAMINA.get()) textureX = 21*22;
            }
            RenderSystem.setShaderTexture(0, EGG_STAMINA);
            GuiComponent.blit(poseStack, x + (segment * (23*mode)), y, textureX, 0, 22, 9, 484, 9);
        }
    }
    private static void horizontalBarRendering(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int x, int y) {
        //Horizontal bar rendering is a rendering type to make the stamina appear in a bar across the screen.
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // empty bar texture
        RenderSystem.setShaderTexture(0, EMPTY_BAR_H);
        // making sure it renders behind
        poseStack.translate(0,0,-5);
        // actually creating it
        GuiComponent.blit(poseStack, x, y, 0, 0, ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get());
        // set the texture to the full bar
        RenderSystem.setShaderTexture(0, FULL_BAR_H);
        // getting the divide amount- I need this so players can customize the actual bar to their heart's content
        int divideAmount = CommonConfig.MAX_STAMINA.get()/ClientConfig.BAR_LENGTH.get();
        // anndd the blit.
        GuiComponent.blit(poseStack, x, y, 0, 0, ClientStaminaData.getPlayerStamina()/divideAmount, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get());
    }
    private static void verticalBarRendering(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        int x = width / 2 - ClientConfig.POSITION_X.get()+ClientConfig.BAR_WIDTH.get();
        int y = height - ClientConfig.POSITION_Y.get()-ClientConfig.BAR_LENGTH.get();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_BAR_V);
        poseStack.translate(0,0,-5);
        GuiComponent.blit(poseStack, x, y, 0, 0, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get(), ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get());
        poseStack.translate(0,0,5);
        RenderSystem.setShaderTexture(0, FULL_BAR_V);
        int divideAmount = ClientStaminaData.getPlayerStamina()/(CommonConfig.MAX_STAMINA.get()/ClientConfig.BAR_LENGTH.get());
        GuiComponent.blit(poseStack, x, y+ClientConfig.BAR_LENGTH.get()-divideAmount, 0, ClientConfig.BAR_LENGTH.get()-divideAmount, ClientConfig.BAR_WIDTH.get(), divideAmount, ClientConfig.BAR_WIDTH.get(), ClientConfig.BAR_LENGTH.get()
        );
    }
    private static void lightningBoltRendering(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int x, int y, int mode) {
        //Lightning bolt rendering is the default renderer, with vanilla style icons to indicate the player's stamina.
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //StaminaFull is for full lightning bolt icons
        double staminaFull = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10F);
        //StaminaEmpty is for empty lightning bolt icons
        double staminaEmpty = Math.round(staminaFull * 2.0) / 2.0;
        staminaFull = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10F);
        //SimpleStamina gets the player's stamina, without decimals
        int simpleStamina = ClientStaminaData.getPlayerStamina() / (CommonConfig.MAX_STAMINA.get() / 10);
        //Looping 10 times to generate all 10 icons
        for (double i = 0; i < staminaFull; i++) {
            RenderSystem.setShaderTexture(0, FILLED_STAMINA);
            int i2 = (int) i;
            GuiComponent.blit(poseStack, x+(i2 * (8*mode)), y, 0, 0, 9, 9, 9, 9);
        }
        for (double i = staminaFull; i < 10; i++) {
            RenderSystem.setShaderTexture(0, EMPTY_STAMINA);
            int i2 = (int) i;
            GuiComponent.blit(poseStack, x+(i2 * (8*mode)), y, 0, 0, 9, 9, 9, 9);
        }
        //Checking if it should be a halved icon or not
        if (Math.abs(staminaFull - simpleStamina) > 0.4) {
            RenderSystem.setShaderTexture(0, HALF_STAMINA);
            int iFull = (int) staminaFull;
            GuiComponent.blit(poseStack, x+(iFull * (8*mode)), y, 0, 0, 9, 9, 9, 9);
        }
        //----> END RESULT <----
        //10 lightning bolt icons in a style similar to vanilla hearts
    }
}

package com.redpxnda.staminawild;

import com.mojang.logging.LogUtils;
import com.redpxnda.staminawild.client.StaminaHudOverlay;
import com.redpxnda.staminawild.config.ClientConfig;
import com.redpxnda.staminawild.config.CommonConfig;
import com.redpxnda.staminawild.packet.Packets;
import com.redpxnda.staminawild.potion.PotionEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("staminawild")
public class StaminaWild {

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public StaminaWild() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        PotionEffects.register(FMLJavaModLoadingContext.get().getModEventBus());
        Attributes.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
        MinecraftForge.EVENT_BUS.register(new StaminaRegen());
        MinecraftForge.EVENT_BUS.register(new Events());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Attributes::AttachAttributes);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "staminawild-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "staminawild-client.toml");
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some preinit code
        event.enqueueWork(Packets::init);
    }
}

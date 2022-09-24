package com.redpxnda.staminawild.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "staminawild")
public class CapEvents {
    @SubscribeEvent
    public static void onAttachCapPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).isPresent()) {
                event.addCapability(new ResourceLocation("staminawild", "properties"), new PlayerStaminaProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
    @SubscribeEvent
    public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
        System.out.println("Hello from capability registry!");
        event.register(PlayerStamina.class);
    }
}

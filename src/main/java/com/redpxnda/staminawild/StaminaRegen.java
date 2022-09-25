package com.redpxnda.staminawild;

import com.redpxnda.staminawild.capability.PlayerStaminaProvider;
import com.redpxnda.staminawild.config.CommonConfig;
import com.redpxnda.staminawild.packet.Packets;
import com.redpxnda.staminawild.packet.StaminaSyncToClientPacket;
import com.redpxnda.staminawild.potion.PotionEffects;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class StaminaRegen {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            if (event.player instanceof ServerPlayer serverPlayer) {
                event.player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                    stamina.depleteRecoveryTime();
                    if (stamina.getStamina() < 10) event.player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), CommonConfig.PLAYER_RECOVERY_TIME.get(), 2, false, false, false));

                    if (stamina.getRecoveryTime() <= 0) {
                        stamina.addStamina(CommonConfig.STAMINA_REGEN.get());
                    }
                    Packets.sendToPlayer(new StaminaSyncToClientPacket(stamina.getStamina()), serverPlayer);
                });
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                    Packets.sendToPlayer(new StaminaSyncToClientPacket(stamina.getStamina()), player);
                });
            }
        }
    }
}

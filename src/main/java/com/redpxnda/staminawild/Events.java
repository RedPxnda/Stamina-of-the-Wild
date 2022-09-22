package com.redpxnda.staminawild;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tictim.paraglider.capabilities.PlayerMovement;

public class Events {

    //player attack
    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        if (CommonConfig.PLAYER_ATTACK_COST.get() > -1) {
            Entity eAttacker = event.getSource().getEntity();
            if (eAttacker instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) eAttacker;
                if (player instanceof Player) {
                    PlayerMovement h = PlayerMovement.of(player);
                    int stamina = h.getStamina();
                    if (stamina < 1) {
                        event.setCanceled(true);
                        player.addEffect(new MobEffectInstance(FatigueEffect.instance, 25, 1, false, false, false));
                    } else {
                        h.takeStamina(CommonConfig.PLAYER_ATTACK_COST.get(), false, true);
                        h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                    }
                }
            }
        }
    }
    //player defend
    @SubscribeEvent
    public void onLivingHurtEvent(LivingDamageEvent event) {
        if (CommonConfig.PLAYER_HIT_COST.get() > -1) {
            LivingEntity defender = event.getEntityLiving();
            LivingEntity player = defender;
            if (player instanceof Player) {
                PlayerMovement h = PlayerMovement.of(player);
                int stamina = h.getStamina();
                if (stamina < 1) {
                    float damage = event.getAmount();
                    event.setAmount(damage + CommonConfig.PLAYER_HIT_ADDITION.get());
                    player.addEffect(new MobEffectInstance(FatigueEffect.instance, 25, 1, false, false, false));
                } else {
                    h.takeStamina(CommonConfig.PLAYER_HIT_COST.get(), false, true);
                    h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                }
            }
        }
    }
    @SubscribeEvent
    public void SprintEvent(TickEvent.PlayerTickEvent event) {
        if (CommonConfig.PLAYER_SPRINT_COST.get() > -1) {
            if (Math.random() * 10 <= 5) {
                if (event.player.isSprinting()) {
                    PlayerMovement h = PlayerMovement.of(event.player);
                    int stamina = h.getStamina();
                    if (stamina < 1) {
                        event.player.addEffect(new MobEffectInstance(FatigueEffect.instance, 25, 1, false, false, false));
                    } else {
                        h.takeStamina(CommonConfig.PLAYER_SPRINT_COST.get(), false, true);
                        h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void JumpEvent(LivingEvent.LivingJumpEvent event) {
        if (CommonConfig.PLAYER_JUMP_COST.get() > -1) {
            Entity ePlayer = event.getEntity();
            if (ePlayer instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) ePlayer;
                if (player.hasEffect(new FatigueEffect())) {
                    player.setDeltaMovement(0, -0.01, 0);
                }
                if (player instanceof Player) {
                    PlayerMovement h = PlayerMovement.of(player);
                    int stamina = h.getStamina();
                    if (stamina < 1) {
                        player.addEffect(new MobEffectInstance(FatigueEffect.instance, 25, 1, false, false, false));
                    } else {
                        h.takeStamina(CommonConfig.PLAYER_JUMP_COST.get(), false, true);
                        h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        if (stamina < 1)
                            player.addEffect(new MobEffectInstance(FatigueEffect.instance, 10, 1, false, false, false));
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        if (CommonConfig.PLAYER_BLOCK_COST.get() > -1) {
            LivingEntity ePlayer = event.getEntityLiving();
            if (ePlayer instanceof Player) {
                Player player = (Player) ePlayer;
                Item shield = player.getOffhandItem().getItem();
                if (player.getMainHandItem().getItem() instanceof ShieldItem) {
                    shield = player.getMainHandItem().getItem();
                }
                PlayerMovement h = PlayerMovement.of(player);
                int stamina = h.getStamina();
                if (stamina < 1) {
                    event.setCanceled(true);
                    if (CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                        player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                    player.addEffect(new MobEffectInstance(FatigueEffect.instance, 25, 1, false, false, false));
                } else {
                    h.takeStamina(CommonConfig.PLAYER_BLOCK_COST.get(), false, true);
                    h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                    stamina = h.getStamina();
                    if (stamina < 1 && CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                        player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                }
            }
        }
    }
}

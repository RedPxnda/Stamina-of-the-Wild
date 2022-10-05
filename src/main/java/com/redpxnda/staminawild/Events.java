package com.redpxnda.staminawild;

import com.redpxnda.staminawild.capability.PlayerStaminaProvider;
import com.redpxnda.staminawild.config.CommonConfig;
import com.redpxnda.staminawild.packet.Packets;
import com.redpxnda.staminawild.packet.StaminaSyncToClientPacket;
import com.redpxnda.staminawild.potion.PotionEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class Events {
    //block mining
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (CommonConfig.PLAYER_MINE_COST.get() > 0) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                    if (Math.random() * 10 <= 5) {
                        if (!player.hasEffect(PotionEffects.FATIGUE.get())) {
                            double itemWeight = player.getAttributeValue(Attributes.ITEM_WEIGHT.get());
                            itemWeight = itemWeight > 0 ? 1 + itemWeight/10 : 1;
                            itemWeight = itemWeight >= 0 ? itemWeight : 0;
                            double dCost = CommonConfig.PLAYER_MINE_COST.get();
                            double cCost = dCost * itemWeight;
                            cCost = Math.ceil(cCost);
                            int finalCost = (int) cCost;
                            h.takeStamina(finalCost);
                            h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        }
                    }
                });
            }
        }
    }
    //player attack
    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event) {
        if (CommonConfig.PLAYER_ATTACK_COST.get() > -1) {
            Entity eAttacker = event.getSource().getEntity();
            if (eAttacker instanceof LivingEntity player) {
                if (player instanceof Player) {
                    player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                        int stamina = h.getStamina();
                        if (!player.hasEffect(PotionEffects.FATIGUE.get())) {
                            double itemWeight = player.getAttributeValue(Attributes.ITEM_WEIGHT.get());
                            itemWeight = itemWeight > 0 ? 1 + itemWeight/10 : 1;
                            itemWeight = itemWeight >= 0 ? itemWeight : 0;
                            double dCost = CommonConfig.PLAYER_ATTACK_COST.get();
                            double cCost = dCost * itemWeight;
                            cCost = Math.ceil(cCost);
                            int finalCost = (int) cCost;
                            h.takeStamina(finalCost);
                            h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        }
                    });
                }
            }
        }
    }
    //player defend
    @SubscribeEvent
    public void onLivingHurtEvent(LivingDamageEvent event) {
        if (CommonConfig.PLAYER_HIT_COST.get() > -1) {
            LivingEntity player = event.getEntity();
            if (player instanceof Player) {
                player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                    if (!player.hasEffect(PotionEffects.FATIGUE.get())) {
                        double armorWeight = player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                        armorWeight = armorWeight > 0 ? 1 - armorWeight/20 : 1;
                        armorWeight = armorWeight >= 0 ? armorWeight : 0;
                        double dCost = CommonConfig.PLAYER_HIT_COST.get();
                        double cCost = dCost * armorWeight;
                        cCost = Math.ceil(cCost);
                        int finalCost = (int) cCost;
                        h.takeStamina(finalCost);
                        h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                    } else {
                        float damage = event.getAmount();
                        event.setAmount(damage + CommonConfig.PLAYER_HIT_ADDITION.get());
                    }
                });
            }
        }
    }
    @SubscribeEvent
    public void SprintEvent(TickEvent.PlayerTickEvent event) {
        if (CommonConfig.PLAYER_SPRINT_COST.get() > -1 && event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                if (Math.random() * 10 <= 5) {
                    int stamina = h.getStamina();
                    if (event.player.isSprinting()) {
                        if (!event.player.hasEffect(PotionEffects.FATIGUE.get())) {
                            double armorWeight = event.player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                            armorWeight = armorWeight > 0 ? 1 + armorWeight/20 : 1;
                            armorWeight = armorWeight >= 0 ? armorWeight : 0;
                            if (stamina > 0) {
                                double dCost = CommonConfig.PLAYER_SPRINT_COST.get();
                                double cCost = dCost * armorWeight;
                                cCost = Math.ceil(cCost);
                                int finalCost = (int) cCost;
                                h.takeStamina(finalCost);
                                h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                            }
                        }
                    }
                }
            });
        }
    }
    @SubscribeEvent
    public void JumpEvent(LivingEvent.LivingJumpEvent event) {
        if (CommonConfig.PLAYER_JUMP_COST.get() > -1) {
            Entity ePlayer = event.getEntity();
            if (ePlayer instanceof LivingEntity player) {
                if (player.hasEffect(PotionEffects.FATIGUE.get())) {
                    player.setDeltaMovement(0, -0.01, 0);
                } else {
                    if (player instanceof Player) {
                        player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                            double armorWeight = player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                            armorWeight = armorWeight > 0 ? 1 + armorWeight/20 : 1;
                            armorWeight = armorWeight >= 0 ? armorWeight : 0;
                            int stamina = h.getStamina();
                            if (stamina > 1) {
                                double dCost = CommonConfig.PLAYER_JUMP_COST.get();
                                double cCost = dCost * armorWeight;
                                cCost = Math.ceil(cCost);
                                int finalCost = (int) cCost;
                                h.takeStamina(finalCost);
                                h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                            }
                        });
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        if (CommonConfig.PLAYER_BLOCK_COST.get() > -1) {
            LivingEntity ePlayer = event.getEntity();
            if (ePlayer instanceof Player player) {
                if (player.hasEffect(PotionEffects.FATIGUE.get())) {
                    event.setCanceled(true);
                } else {
                    player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(h -> {
                        Item shield = player.getOffhandItem().getItem();
                        if (player.getMainHandItem().getItem() instanceof ShieldItem) {
                            shield = player.getMainHandItem().getItem();
                        }
                        double shieldStab = player.getAttributeValue(Attributes.SHIELD_STABILITY.get());
                        shieldStab = shieldStab > 0 ? 1 - shieldStab/10 : 1;
                        shieldStab = shieldStab >= 0 ? shieldStab : 0;
                        int stamina = h.getStamina();
                        if (stamina < 1) {
                            //event.setCanceled(true);
                            if (CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                                player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                        } else {
                            double dCost = CommonConfig.PLAYER_BLOCK_COST.get();
                            double cCost = dCost * shieldStab;
                            cCost = Math.ceil(cCost);
                            int finalCost = (int) cCost;
                            h.takeStamina(finalCost);
                            h.setRecoveryTime(CommonConfig.PLAYER_RECOVERY_TIME.get());
                            stamina = h.getStamina();
                            if (stamina < 1 && CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                                player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                        }
                    });
                }
            }
        }
    }
}

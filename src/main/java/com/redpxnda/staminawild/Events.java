package com.redpxnda.staminawild;

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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tictim.paraglider.capabilities.PlayerMovement;

public class Events {
    //player attack
    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event) {
        if (CommonConfig.PLAYER_ATTACK_COST.get() > -1) {
            Entity eAttacker = event.getSource().getEntity();
            if (eAttacker instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) eAttacker;
                if (player instanceof Player) {
                    PlayerMovement h = PlayerMovement.of(player);
                    int stamina = h.getStamina();
                    if (!player.hasEffect(PotionEffects.FATIGUE.get())) {
                        double itemWeight = player.getAttributeValue(Attributes.ITEM_WEIGHT.get());
                        if (itemWeight < 0) {
                            itemWeight = itemWeight * -1;
                            if (1 - itemWeight / 5 > 0) {
                                itemWeight = 1 - itemWeight / 20;
                            } else {
                                itemWeight = 0;
                            }
                        } else {
                            itemWeight = 1 + itemWeight / 5;
                        }
                        int cost = CommonConfig.PLAYER_ATTACK_COST.get();
                        double dCost = cost;
                        double cCost = dCost * itemWeight;
                        cCost = Math.round(cCost);
                        int finalCost = (int) cCost;
                        h.takeStamina(finalCost, false, true);
                        h.update();
                        h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        if (stamina < 1)
                            player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
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
                if (!player.hasEffect(PotionEffects.FATIGUE.get())) {
                    double armorWeight = player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                    if (armorWeight < 0) {
                        armorWeight = armorWeight * -1;
                        if (1 - armorWeight/5 > 0) {
                            armorWeight = 1 - armorWeight/20;
                        } else {
                            armorWeight = 0;
                        }
                    } else {
                        armorWeight = 1 + armorWeight / 5;
                    }
                    int cost = CommonConfig.PLAYER_HIT_COST.get();
                    double dCost = cost;
                    double cCost = dCost * armorWeight;
                    cCost = Math.round(cCost);
                    int finalCost = (int) cCost;
                    h.takeStamina(finalCost, false, true);
                    h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                } else {
                    if (stamina < 1)
                        player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
                    float damage = event.getAmount();
                    event.setAmount(damage + CommonConfig.PLAYER_HIT_ADDITION.get());
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
                    if (!event.player.hasEffect(PotionEffects.FATIGUE.get())) {
                        double armorWeight = event.player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                        if (armorWeight < 0) {
                            armorWeight = armorWeight * -1;
                            if (1 - armorWeight / 5 > 0) {
                                armorWeight = 1 - armorWeight / 20;
                            } else {
                                armorWeight = 0;
                            }
                        } else {
                            armorWeight = 1 + armorWeight / 5;
                        }
                        if (stamina < 1) {
                            event.player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
                        } else {
                            int cost = CommonConfig.PLAYER_SPRINT_COST.get();
                            double dCost = cost;
                            double cCost = dCost * armorWeight;
                            cCost = Math.round(cCost);
                            int finalCost = (int) cCost;
                            h.takeStamina(finalCost, false, true);
                            h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        }
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
                if (player.hasEffect(PotionEffects.FATIGUE.get())) {
                    player.setDeltaMovement(0, -0.01, 0);
                } else {
                    if (player instanceof Player) {
                        double armorWeight = player.getAttributeValue(Attributes.ARMOR_WEIGHT.get());
                        if (armorWeight < 0) {
                            armorWeight = armorWeight * -1;
                            if (1 - armorWeight / 5 > 0) {
                                armorWeight = 1 - armorWeight / 20;
                            } else {
                                armorWeight = 0;
                            }
                        } else {
                            armorWeight = 1 + armorWeight / 5;
                        }
                        PlayerMovement h = PlayerMovement.of(player);
                        int stamina = h.getStamina();
                        if (stamina < 1) {
                            player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
                        } else {
                            int cost = CommonConfig.PLAYER_JUMP_COST.get();
                            double dCost = cost;
                            double cCost = dCost * armorWeight;
                            cCost = Math.round(cCost);
                            int finalCost = (int) cCost;
                            h.takeStamina(finalCost, false, true);
                            h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                            if (stamina < 1)
                                player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
                        }
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
                if (player.hasEffect(PotionEffects.FATIGUE.get())) {
                    event.setCanceled(true);
                } else {
                    Item shield = player.getOffhandItem().getItem();
                    if (player.getMainHandItem().getItem() instanceof ShieldItem) {
                        shield = player.getMainHandItem().getItem();
                    }
                    double shieldStab = player.getAttributeValue(Attributes.SHIELD_STABILITY.get());
                    if (shieldStab < 0) {
                        shieldStab = shieldStab * -1;
                        if (1 - shieldStab / 5 > 0) {
                            shieldStab = 1 - shieldStab / 20;
                        } else {
                            shieldStab = 0;
                        }
                    } else {
                        shieldStab = 1 + shieldStab / 5;
                    }
                    PlayerMovement h = PlayerMovement.of(player);
                    int stamina = h.getStamina();
                    if (stamina < 1) {
                        //event.setCanceled(true);
                        if (CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                            player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                        player.addEffect(new MobEffectInstance(PotionEffects.FATIGUE.get(), 60, 2, false, false, false));
                    } else {
                        int cost = CommonConfig.PLAYER_BLOCK_COST.get();
                        double dCost = cost;
                        double cCost = dCost * shieldStab;
                        cCost = Math.round(cCost);
                        int finalCost = (int) cCost;
                        h.takeStamina(finalCost, false, true);
                        h.setRecoveryDelay(CommonConfig.PLAYER_RECOVERY_TIME.get());
                        stamina = h.getStamina();
                        if (stamina < 1 && CommonConfig.PLAYER_BLOCK_PENALTY.get() > -1)
                            player.getCooldowns().addCooldown(shield, CommonConfig.PLAYER_BLOCK_PENALTY.get());
                    }
                }
            }
        }
    }
}

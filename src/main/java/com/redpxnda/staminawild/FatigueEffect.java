package com.redpxnda.staminawild;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FatigueEffect extends MobEffect {
    public static FatigueEffect instance;
    public FatigueEffect() {
        super(MobEffectCategory.HARMFUL, 0xeeeeee);

        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1d4f7609-7d47-4bcf-a434-b9f2812f60a4", -0.15D, AttributeModifier.Operation.MULTIPLY_BASE);
        addAttributeModifier(Attributes.ATTACK_SPEED, "d105c2f7-d3c4-449b-a84f-cd3c2fe57d8e", -0.1D, AttributeModifier.Operation.MULTIPLY_BASE);

        instance = this;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}
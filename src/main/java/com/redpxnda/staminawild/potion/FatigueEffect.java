package com.redpxnda.staminawild.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FatigueEffect extends MobEffect {
    public static FatigueEffect instance;
    public FatigueEffect() {
        super(MobEffectCategory.HARMFUL, 0xeeeeee);

        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1d4f7609-7d47-4bcf-a434-b9f2812f60a4", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "d105c2f7-d3c4-449b-a84f-cd3c2fe57d8e", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "d105c2f7-d3c4-449b-a84f-cd3c2fe57d8e", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL);

        instance = this;
    }
    public void applyEffectTick(LivingEntity pEntity, int pAmplifier) {

    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }
}
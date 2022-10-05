package com.redpxnda.staminawild;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class Attributes {
    public static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "staminawild");
    public static final RegistryObject<Attribute> ARMOR_WEIGHT = ATTRIBUTES
            .register("armor_weight",
                    () -> (Attribute) new RangedAttribute(
                            "attribute.staminawild.armor_weight",
                            0,
                            0.0D,
                            1024.0D)
                            .setSyncable(true)
            );
    public static final RegistryObject<Attribute> SHIELD_STABILITY = ATTRIBUTES
            .register("shield_stability",
                    () -> (Attribute) new RangedAttribute(
                            "attribute.staminawild.shield_stability",
                            0,
                            0.0D,
                            1024.0D)
                            .setSyncable(true)
            );
    public static final RegistryObject<Attribute> ITEM_WEIGHT = ATTRIBUTES
            .register("item_weight",
                    () -> (Attribute) new RangedAttribute(
                            "attribute.staminawild.item_weight",
                            0,
                            0.0D,
                            1024.0D)
                            .setSyncable(true)
            );
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    @SubscribeEvent
    public static void AttachAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ARMOR_WEIGHT.get(), 0.0);
        event.add(EntityType.PLAYER, SHIELD_STABILITY.get(), 0.0);
        event.add(EntityType.PLAYER, ITEM_WEIGHT.get(), 0.0);
    }
}

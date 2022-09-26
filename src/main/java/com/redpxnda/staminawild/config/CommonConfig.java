package com.redpxnda.staminawild.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_ATTACK_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_HIT_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_HIT_ADDITION;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_SPRINT_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_JUMP_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_BLOCK_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_BLOCK_PENALTY;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_MINE_COST;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_RECOVERY_TIME;
    public static final ForgeConfigSpec.ConfigValue<Integer> STAMINA_REGEN;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_STAMINA;

    static {
        BUILDER.push("Config for Stamina of the Wild");

        PLAYER_ATTACK_COST = BUILDER.comment("How much stamina attacking costs. Set to -1 to disable. ")
                .define("Attack Cost", 15);
        PLAYER_HIT_COST = BUILDER.comment("How much stamina getting hit costs. Set to -1 to disable. ")
                .define("Hit Cost", 20);
        PLAYER_HIT_ADDITION = BUILDER.comment("How much additional damage is dealt if the player has no stamina(in half hearts). Set to 0 to disable.")
                .define("Hit Addition", 3);
        PLAYER_SPRINT_COST = BUILDER.comment("How much stamina sprinting costs every ~5 ticks. Set to -1 to disable. ")
                .define("Sprint Cost", 1);
        PLAYER_JUMP_COST = BUILDER.comment("How much stamina jumping costs. Set to -1 to disable. ")
                .define("Jump Cost", 10);
        PLAYER_BLOCK_COST = BUILDER.comment("How much stamina blocking with a shield costs. Set to -1 to disable. ")
                .define("Shield Cost", 35);
        PLAYER_BLOCK_PENALTY = BUILDER.comment("How long shields are disabled for after losing full stamina(in ticks). Set to -1 to disable.")
                .define("Shield Penalty", 40);
        PLAYER_MINE_COST = BUILDER.comment("How much stamina is consumed every ~5 ticks a player is mining. Set to -1 to disable.")
                .define("Mine Cost", 1);
        PLAYER_RECOVERY_TIME = BUILDER.comment("How long it takes for stamina to start regenning(in ticks). Set to -1 to disable.")
                .define("Recovery Time", 60);
        STAMINA_REGEN = BUILDER.comment("How much stamina is regenerated per tick. Must be > 1 and <= 1000")
                .define("Stamina Regen", 1);
        MAX_STAMINA = BUILDER.comment("-----> EXPERIMENTAL <-----\nMax stamina a player can have. MUST be a multiple of 10.\nIt is recommended to not go below 100, otherwise things may break.")
                .define("Max Stamina", 1000);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

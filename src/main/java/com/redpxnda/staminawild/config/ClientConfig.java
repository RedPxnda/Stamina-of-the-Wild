package com.redpxnda.staminawild.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> RENDER_MODE;
    public static final ForgeConfigSpec.ConfigValue<Integer> SEGMENTS;
    public static final ForgeConfigSpec.ConfigValue<Integer> BAR_LENGTH;
    public static final ForgeConfigSpec.ConfigValue<Integer> BAR_WIDTH;
    public static final ForgeConfigSpec.ConfigValue<Integer> POSITION_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> POSITION_Y;
    public static final ForgeConfigSpec.ConfigValue<String> POSITION_PRESET;

    static {
        BUILDER.push("Client Config for Stamina of the Wild");

        RENDER_MODE = BUILDER.comment("Rendering mode for stamina...\nValid Options: 'Default', 'Horizontal Bar', 'Vertical Bar', 'Segments'")
                .define("Render mode", "Default");
        SEGMENTS = BUILDER.comment("Amount of segments for the 'Segments' render mode.")
                .define("Segments", 5);
        BAR_LENGTH = BUILDER.comment("Length of the bar (for both Horizontal and Vertical modes) in pixels.\nOnly change this if you changed the bar texture.")
                .define("Bar Length", 100);
        BAR_WIDTH = BUILDER.comment("Width of the bar (for both Horizontal and Vertical modes) in pixels.\nOnly change this if you changed the bar texture.")
                .define("Bar Width", 20);
        POSITION_PRESET = BUILDER.comment("Premade presets to help anchor your render to parts of the screen.\nAvailable presets: 'bottom_left', 'bottom_right', 'top_left', 'top_right', 'custom'")
                .define("Position Preset", "bottom_left");
        POSITION_X = BUILDER.comment("X offset of the render. (Horizontally)\nNegative values move to the left, positive move to the right.")
                .define("X Offset", 0);
        POSITION_Y = BUILDER.comment("Y offset of the render. (Vertically)\nNegative values move down, positive move up.")
                .define("Y Offset", 0);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

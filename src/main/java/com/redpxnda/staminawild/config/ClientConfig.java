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

    static {
        BUILDER.push("Client Config for Stamina of the Wild");

        RENDER_MODE = BUILDER.comment("Rendering mode for stamina...\nValid Options: 'Default', 'Horizontal Bar', 'Vertical Bar', 'Segments', 'Crosshair'")
                .define("Render mode", "Default");
        SEGMENTS = BUILDER.comment("Amount of segments for the 'Segments' render mode.")
                .define("Segments", 5);
        BAR_LENGTH = BUILDER.comment("Length of the bar (for both Horizontal and Vertical modes) in pixels.\nOnly change this if you changed the bar texture.")
                .define("Bar Length", 200);
        BAR_WIDTH = BUILDER.comment("Width of the bar (for both Horizontal and Vertical modes) in pixels.\nOnly change this if you changed the bar texture.")
                .define("Bar Width", 20);
        POSITION_X = BUILDER.comment("X offset of the render. (Horizontally)\nCalculated by: SCREENWIDTH/2-X")
                .define("X Offset", 94);
        POSITION_Y = BUILDER.comment("Y offset of the render. (Vertically)\nCalculated by: SCREENHEIGHT-Y")
                .define("Y Offset", 54);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

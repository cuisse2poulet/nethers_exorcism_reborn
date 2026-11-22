package net.enorme.NER;

import net.neoforged.neoforge.common.ModConfigSpec;


public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        BUILDER.comment("Indigo Scyphozoa settings").push("scyphozoa");
    }

    public static final ModConfigSpec.BooleanValue SCYPHOZOA_LEAP_ENABLED = BUILDER
            .comment("Whether Indigo Scyphozoa randomly leap forward while idle")
            .define("leapEnabled", true);

    public static final ModConfigSpec.IntValue SCYPHOZOA_LEAP_MIN_COOLDOWN = BUILDER
            .comment("Minimum ticks between leaps (20 ticks = 1 second)")
            .defineInRange("leapMinCooldown", 160, 20, 72000);

    public static final ModConfigSpec.IntValue SCYPHOZOA_LEAP_RANDOM_COOLDOWN = BUILDER
            .comment("Extra random ticks added on top of the minimum cooldown (0 = always exactly the minimum)")
            .defineInRange("leapRandomCooldown", 240, 0, 72000);

    static {
        BUILDER.pop();
    }

    static final ModConfigSpec SPEC = BUILDER.build();

}

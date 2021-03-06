package xyz.phanta.clochepp;

import net.minecraftforge.common.config.Config;

@Config(modid = ClochePP.MOD_ID)
public class CppConfig {

    @Config.Comment({
            "A list of IDs of Cloche++ modules that should not be loaded.",
            "Existing modules are: agricraft, mysticalagradditions, mysticalagriculture"
    })
    @Config.RequiresMcRestart
    public static String[] disabledModules = {};

    @Config.Comment({
            "A list of module ID/component ID pairs that should be disabled.",
            "Each entry should be formatted as `moduleid:componentid` with a colon as a separator.",
            "Component IDs of all loaded modules can be discovered by checking the debug log."
    })
    @Config.RequiresMcRestart
    public static String[] disabledComponents = {};

    @Config.Comment("The strength of mystical agriculture fertilizer, if that component is enabled.")
    @Config.RangeDouble(min = 0D)
    public static double mystAgriFertStrength = 1.65D;

    @Config.Comment({
            "The number of simulated neighboring plants to use for generating AgriCraft seeds.",
            "If set to 0, no seeds will be dropped whatsoever.",
            "If set to 1, seed drops will be a clone of the original seed REGARDLESS of how AgriCraft is configured.",
            "AgriCraft's built-in \"single spread stat increase\" option is equivalent to setting this to 2."
    })
    @Config.RangeInt(min = 0, max = 8)
    public static int agriCraftSeedSpreadNeighborCount = 1;

    @Config.Comment({
            "The growth rate modifier for AgriCraft plants.",
            "Normally, the this is calculated based on the AgriCraft growth rate compared to that of vanilla plants.",
            "If you want to tweak it further, this multiplier will be applied to the growth rate. This should probably be >0."
    })
    @Config.RangeDouble(min = 0)
    public static double agriCraftGrowthRateModifier = 1D;

}

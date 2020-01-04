package xyz.phanta.clochepp;

import net.minecraftforge.common.config.Config;

@Config(modid = ClochePP.MOD_ID)
public class CppConfig {

    @Config.Comment("A list of IDs of Cloche++ modules that should not be loaded.")
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
    @Config.RequiresMcRestart
    public static double mystAgriFertStrength = 1.65D;

}

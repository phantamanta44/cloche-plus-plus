package xyz.phanta.clochepp.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.clochepp.ClochePP;
import xyz.phanta.clochepp.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new ConfigGuiReloadHandler());
        switchboard.preInitClient();
    }

    private static class ConfigGuiReloadHandler {

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ClochePP.MOD_ID)) {
                ConfigManager.sync(ClochePP.MOD_ID, Config.Type.INSTANCE);
            }
        }

    }

}

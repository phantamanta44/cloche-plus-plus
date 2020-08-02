package xyz.phanta.clochepp;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ClochePP.MOD_ID, version = ClochePP.VERSION, useMetadata = true)
public class ClochePP {

    public static final String MOD_ID = "clochepp";
    public static final String VERSION = "1.0.3";

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Mod.Instance(MOD_ID)
    public static ClochePP INSTANCE;

    @SuppressWarnings("NotNullFieldNotInitialized")
    @SidedProxy(
            clientSide = "xyz.phanta.clochepp.client.ClientProxy",
            serverSide = "xyz.phanta.clochepp.CommonProxy")
    public static CommonProxy PROXY;

    @SuppressWarnings("NotNullFieldNotInitialized")
    public static Logger LOGGER;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        PROXY.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

}

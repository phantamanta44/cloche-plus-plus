package xyz.phanta.clochepp.client;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.phanta.clochepp.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        switchboard.preInitClient();
    }

}

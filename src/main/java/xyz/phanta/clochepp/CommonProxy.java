package xyz.phanta.clochepp;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.phanta.clochepp.module.ClocheModule;
import xyz.phanta.clochepp.module.ModuleSwitchboard;

import java.util.List;
import java.util.Map;

public class CommonProxy {

    protected final ModuleSwitchboard switchboard = new ModuleSwitchboard();

    @SuppressWarnings("unchecked")
    public void onPreInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData asmData : event.getAsmData().getAll(ClocheModule.Register.class.getName())) {
            Map<String, Object> props = asmData.getAnnotationInfo();
            String name = (String)props.get("name");
            String id = (String)props.get("id");
            List<String> deps = (List<String>)props.get("deps");
            switchboard.offerModuleCandidate(asmData.getObjectName(), name, id, deps);
        }
        switchboard.preInit();
    }

    public void onInit(FMLInitializationEvent event) {
        switchboard.init();
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        switchboard.postInit();
    }

}

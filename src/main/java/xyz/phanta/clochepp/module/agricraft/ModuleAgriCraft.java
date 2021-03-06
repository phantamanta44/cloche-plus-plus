package xyz.phanta.clochepp.module.agricraft;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import xyz.phanta.clochepp.module.ClocheModule;
import xyz.phanta.clochepp.moduleapi.ClocheComponent;
import xyz.phanta.clochepp.moduleapi.ClocheRegistrar;
import xyz.phanta.clochepp.moduleapi.ComponentRegistrar;
import xyz.phanta.clochepp.util.ImmEngReflect;

import java.util.HashSet;
import java.util.LinkedHashSet;

@SuppressWarnings("NotNullFieldNotInitialized")
@ClocheModule.Register(name = "AgriCraft", id = "agricraft", deps = { ModuleAgriCraft.MOD_ID })
public class ModuleAgriCraft implements ClocheModule {

    public static final String MOD_ID = "agricraft";

    private ClocheComponent cAgriCraftPlants, cForcePriority;

    @Override
    public void registerComponents(ComponentRegistrar registrar) {
        cAgriCraftPlants = registrar.registerComponent("agricraft_plants");
        cForcePriority = registrar.registerComponent("force_priority");
    }

    @Override
    public void register(ClocheRegistrar registrar) {
        cAgriCraftPlants.ifEnabled(() -> {
            if (cForcePriority.isEnabled()) {
                // force priority for this handler so other handlers don't get to agricraft-enabled seeds first
                HashSet<BelljarHandler.IPlantHandler> handlers = ImmEngReflect.getPlantHandlers();
                HashSet<BelljarHandler.IPlantHandler> newSet = new LinkedHashSet<>();
                newSet.add(new AgriPlantHandler());
                newSet.addAll(handlers);
                ImmEngReflect.setPlantHandlers(newSet);
            } else {
                registrar.registerPlantHandler(new AgriPlantHandler());
            }
        });
    }

}

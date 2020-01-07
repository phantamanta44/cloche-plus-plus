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

    private ClocheComponent cAgriCraftPlants, cForcePriority, cSeedDrops;

    @Override
    public void registerComponents(ComponentRegistrar registrar) {
        cAgriCraftPlants = registrar.registerComponent("agricraft_plants");
        cForcePriority = registrar.registerComponent("force_priority");
        cSeedDrops = registrar.registerComponent("seed_drops");
    }

    @Override
    public void register(ClocheRegistrar registrar) {
        cAgriCraftPlants.ifEnabled(() -> {
            if (cForcePriority.isEnabled()) {
                // force priority for this handler so other handlers don't get to agricraft-enabled seeds first
                HashSet<BelljarHandler.IPlantHandler> handlers = ImmEngReflect.getPlantHandlers();
                HashSet<BelljarHandler.IPlantHandler> newSet = new LinkedHashSet<>();
                newSet.add(createPlantHandler());
                newSet.addAll(handlers);
                ImmEngReflect.setPlantHandlers(newSet);
            } else {
                registrar.registerPlantHandler(createPlantHandler());
            }
        });
    }

    private BelljarHandler.IPlantHandler createPlantHandler() {
        return new AgriPlantHandler(cSeedDrops.isEnabled());
    }

}

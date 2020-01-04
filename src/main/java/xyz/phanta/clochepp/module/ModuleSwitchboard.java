package xyz.phanta.clochepp.module;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.Loader;
import xyz.phanta.clochepp.ClochePP;
import xyz.phanta.clochepp.CppConfig;
import xyz.phanta.clochepp.component.ComponentManager;
import xyz.phanta.clochepp.moduleapi.ClocheRegistrar;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ModuleSwitchboard {

    private static final Set<String> disabledModules = Sets.newHashSet(CppConfig.disabledModules);

    private final List<LoadedModule> loadedModules = new ArrayList<>();
    private final ComponentManager components = new ComponentManager();

    public void offerModuleCandidate(String className, String name, String id, @Nullable List<String> deps) {
        if (disabledModules.contains(name)) {
            ClochePP.LOGGER.info("Ignoring module {} ({}), since it's disabled in the config.", name, id);
            return;
        }
        List<String> missingDeps = new ArrayList<>();
        if (deps != null) {
            for (String dep : deps) {
                if (!Loader.isModLoaded(dep)) {
                    missingDeps.add(dep);
                }
            }
        }
        if (!missingDeps.isEmpty()) {
            ClochePP.LOGGER.info("Ignoring module {} ({}), since dep(s) {} are not met.",
                    name, id, String.join(", ", missingDeps));
        } else {
            try {
                loadedModules.add(new LoadedModule(name, id, (ClocheModule)Class.forName(className).newInstance()));
                ClochePP.LOGGER.info("Loaded module {} ({}).", name, id);
            } catch (Exception e) {
                ClochePP.LOGGER.error("Encountered {} while loading module {} ({})!", e.getClass().getName(), name, id);
                ClochePP.LOGGER.error(e);
            }
        }
    }

    public void preInit() {
        performModuleAction(m -> m.instance.init(), "initializing", "Initialized");
    }

    public void preInitClient() {
        performModuleAction(m -> m.instance.initClient(), "client-initializing", "Client-initialized");
    }

    public void init() {
        performModuleAction(m -> m.instance.registerComponents((c, d) -> components.getOrPut(m.name, c, d)),
                "registering components for", "Registered components for");
    }

    public void postInit() {
        ClocheRegistrar registrar = new BellJarHandlerWrapper();
        performModuleAction(m -> m.instance.register(registrar), "registering data for", "Registered data for");
    }

    private void performModuleAction(Consumer<LoadedModule> action, String nameProg, String namePast) {
        for (LoadedModule module : loadedModules) {
            try {
                action.accept(module);
                ClochePP.LOGGER.debug("{} module {}.", namePast, module);
            } catch (Exception e) {
                ClochePP.LOGGER.error("Encountered {} while {} module {}!", e.getClass().getSimpleName(), nameProg, module);
                ClochePP.LOGGER.error("Stack trace:", e);
            }
        }
    }

    private static class LoadedModule {

        final String name, id;
        final ClocheModule instance;

        LoadedModule(String name, String id, ClocheModule instance) {
            this.name = name;
            this.id = id;
            this.instance = instance;
        }

        @Override
        public String toString() {
            return name + " (" + id + ")";
        }

    }

    private static class BellJarHandlerWrapper implements ClocheRegistrar {

        @Override
        public void registerPlantHandler(BelljarHandler.IPlantHandler handler) {
            BelljarHandler.registerHandler(handler);
        }

        @Override
        public void registerFertilizerItemHandler(BelljarHandler.ItemFertilizerHandler handler) {
            BelljarHandler.registerItemFertilizer(handler);
        }

        @Override
        public void registerFertilizerFluidHandler(BelljarHandler.FluidFertilizerHandler handler) {
            BelljarHandler.registerFluidFertilizer(handler);
        }

    }

}

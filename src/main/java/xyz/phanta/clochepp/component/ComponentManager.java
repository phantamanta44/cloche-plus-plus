package xyz.phanta.clochepp.component;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import net.minecraftforge.fml.common.Loader;
import xyz.phanta.clochepp.ClochePP;
import xyz.phanta.clochepp.CppConfig;
import xyz.phanta.clochepp.moduleapi.ClocheComponent;

import java.util.HashSet;
import java.util.Set;

public class ComponentManager {

    private static final Set<String> disabledComponents = Sets.newHashSet(CppConfig.disabledComponents);

    private final Table<String, String, ComponentState> components = HashBasedTable.create();

    public ClocheComponent getOrPut(String moduleId, String name, String... deps) {
        ComponentState component = components.get(moduleId, name);
        if (component == null) {
            ClochePP.LOGGER.debug("Module {} registered new component {}.", moduleId, name);
            boolean enabled;
            if (disabledComponents.contains(moduleId + ":" + name)) {
                ClochePP.LOGGER.debug("Component {}:{} disabled via config.", moduleId, name);
                enabled = false;
            } else {
                enabled = true;
                for (String dep : deps) {
                    if (!Loader.isModLoaded(dep)) {
                        ClochePP.LOGGER.debug("Component {}:{} is missing dependency {}.", moduleId, name, dep);
                        enabled = false;
                    }
                }
            }
            component = new ComponentState(enabled);
            components.put(moduleId, name, component);
        }
        return component;
    }

    private static class ComponentState implements ClocheComponent {

        private final boolean enabled;
        private final Set<String> deps = new HashSet<>();

        ComponentState(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

    }

}

package xyz.phanta.clochepp.module.mystagri;

import net.minecraft.item.ItemStack;
import xyz.phanta.clochepp.CppConfig;
import xyz.phanta.clochepp.cloche.SimpleFertilizerItemHandler;
import xyz.phanta.clochepp.module.ClocheModule;
import xyz.phanta.clochepp.moduleapi.ClocheComponent;
import xyz.phanta.clochepp.moduleapi.ClocheRegistrar;
import xyz.phanta.clochepp.moduleapi.ComponentRegistrar;

@SuppressWarnings("NotNullFieldNotInitialized")
@ClocheModule.Register(name = "Mystical Agriculture", id = "mystagri", deps = { ModuleMysticalAgriculture.MOD_ID })
public class ModuleMysticalAgriculture implements ClocheModule {

    public static final String MOD_ID = "mysticalagriculture";

    private ClocheComponent cMysticalFertilizer;

    @Override
    public void registerComponents(ComponentRegistrar registrar) {
        cMysticalFertilizer = registrar.registerComponent("mystical_fertilizer");
    }

    @Override
    public void register(ClocheRegistrar registrar) {
        SimpleFertilizerItemHandler handler = new SimpleFertilizerItemHandler(false, false);
        cMysticalFertilizer.ifEnabled(() -> handler.addEntry(
                new SimpleFertilizerItemHandler.Entry(new ItemStack(MysticalAgricultureContent.MYSTICAL_FERTILIZER),
                        () -> (float)CppConfig.mystAgriFertStrength)));
        if (!handler.isEmpty()) {
            registrar.registerFertilizerItemHandler(handler);
        }
    }

}

package xyz.phanta.clochepp.module.mystagri;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("NotNullFieldNotInitialized")
@GameRegistry.ObjectHolder(ModuleMysticalAgriculture.MOD_ID)
public class MysticalAgricultureContent {

    @GameRegistry.ObjectHolder("crafting")
    public static Item CRAFTING;
    @GameRegistry.ObjectHolder("mystical_fertilizer")
    public static Item MYSTICAL_FERTILIZER;

}

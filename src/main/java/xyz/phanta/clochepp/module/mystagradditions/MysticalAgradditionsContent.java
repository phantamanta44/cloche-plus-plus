package xyz.phanta.clochepp.module.mystagradditions;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("NotNullFieldNotInitialized")
@GameRegistry.ObjectHolder(ModuleMysticalAgradditions.MOD_ID)
public class MysticalAgradditionsContent {

    @GameRegistry.ObjectHolder("special")
    public static Block SPECIAL;

    @GameRegistry.ObjectHolder("tier6_inferium_seeds")
    public static Item TIER6_INFERIUM_SEEDS;
    @GameRegistry.ObjectHolder("tier6_inferium_crop")
    public static Block TIER6_INFERIUM_CROP;

    @GameRegistry.ObjectHolder("nether_star_seeds")
    public static Item NETHER_STAR_SEEDS;
    @GameRegistry.ObjectHolder("nether_star_essence")
    public static Item NETHER_STAR_ESSENCE;
    @GameRegistry.ObjectHolder("nether_star_crop")
    public static Block NETHER_STAR_CROP;

    @GameRegistry.ObjectHolder("dragon_egg_seeds")
    public static Item DRAGON_EGG_SEEDS;
    @GameRegistry.ObjectHolder("dragon_egg_essence")
    public static Item DRAGON_EGG_ESSENCE;
    @GameRegistry.ObjectHolder("dragon_egg_crop")
    public static Block DRAGON_EGG_CROP;

    @GameRegistry.ObjectHolder("awakened_draconium_seeds")
    public static Item AWAKENED_DRACONIUM_SEEDS;
    @GameRegistry.ObjectHolder("awakened_draconium_essence")
    public static Item AWAKENED_DRACONIUM_ESSENCE;
    @GameRegistry.ObjectHolder("awakened_draconium_crop")
    public static Block AWAKENED_DRACONIUM_CROP;

    @GameRegistry.ObjectHolder("neutronium_seeds")
    public static Item NEUTRONIUM_SEEDS;
    @GameRegistry.ObjectHolder("neutronium_essence")
    public static Item NEUTRONIUM_ESSENCE;
    @GameRegistry.ObjectHolder("neutronium_crop")
    public static Block NEUTRONIUM_CROP;

}

package xyz.phanta.clochepp.module.mystagradditions;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.ItemStack;
import xyz.phanta.clochepp.cloche.SimplePlantHandler;
import xyz.phanta.clochepp.cloche.SoilType;
import xyz.phanta.clochepp.module.ClocheModule;
import xyz.phanta.clochepp.module.mystagri.ModuleMysticalAgriculture;
import xyz.phanta.clochepp.module.mystagri.MysticalAgricultureContent;
import xyz.phanta.clochepp.moduleapi.ClocheComponent;
import xyz.phanta.clochepp.moduleapi.ClocheRegistrar;
import xyz.phanta.clochepp.moduleapi.ComponentRegistrar;

@SuppressWarnings("NotNullFieldNotInitialized")
@ClocheModule.Register(
        name = "Mystical Agradditions", id = "mystagradditions",
        deps = { ModuleMysticalAgriculture.MOD_ID, ModuleMysticalAgradditions.MOD_ID }
)
public class ModuleMysticalAgradditions implements ClocheModule {

    public static final String MOD_ID = "mysticalagradditions";

    private ClocheComponent cTier6InferiumSeeds, cNetherStarSeeds, cDragonEggSeeds, cAwakenedDraconiumSeeds, cNeutroniumSeeds;

    @Override
    public void registerComponents(ComponentRegistrar registrar) {
        cTier6InferiumSeeds = registrar.registerComponent("tier6_inferium_seeds", ModuleMysticalAgriculture.MOD_ID);
        cNetherStarSeeds = registrar.registerComponent("nether_star_seeds");
        cDragonEggSeeds = registrar.registerComponent("dragon_egg_seeds");
        cAwakenedDraconiumSeeds = registrar.registerComponent("awakened_draconium_seeds", "draconicevolution");
        cNeutroniumSeeds = registrar.registerComponent("neutronium_seeds", "avaritia");
    }

    @Override
    public void register(ClocheRegistrar registrar) {
        SimplePlantHandler handler = new SimplePlantHandler(false, false);
        cTier6InferiumSeeds.ifEnabled(() -> handler.addEntry(
                new SimplePlantHandler.Entry(new ItemStack(MysticalAgradditionsContent.TIER6_INFERIUM_SEEDS),
                        new ItemStack(MysticalAgricultureContent.CRAFTING, 6, 0))
                        .withGrowthStageVar(MysticalAgradditionsContent.TIER6_INFERIUM_CROP, BlockCrops.AGE)));
        cNetherStarSeeds.ifEnabled(() -> handler.addEntry(
                new SimplePlantHandler.Entry(new ItemStack(MysticalAgradditionsContent.NETHER_STAR_SEEDS),
                        new ItemStack(MysticalAgradditionsContent.NETHER_STAR_ESSENCE))
                        .withSoilType(SoilType.exact(new ItemStack(MysticalAgradditionsContent.SPECIAL, 1, 0), null))
                        .withGrowthStageVar(MysticalAgradditionsContent.NETHER_STAR_CROP, BlockCrops.AGE)));
        cDragonEggSeeds.ifEnabled(() -> handler.addEntry(
                new SimplePlantHandler.Entry(new ItemStack(MysticalAgradditionsContent.DRAGON_EGG_SEEDS),
                        new ItemStack(MysticalAgradditionsContent.DRAGON_EGG_ESSENCE))
                        .withSoilType(SoilType.exact(new ItemStack(MysticalAgradditionsContent.SPECIAL, 1, 1), null))
                        .withGrowthStageVar(MysticalAgradditionsContent.DRAGON_EGG_CROP, BlockCrops.AGE)));
        cAwakenedDraconiumSeeds.ifEnabled(() -> handler.addEntry(
                new SimplePlantHandler.Entry(new ItemStack(MysticalAgradditionsContent.AWAKENED_DRACONIUM_SEEDS),
                        new ItemStack(MysticalAgradditionsContent.AWAKENED_DRACONIUM_ESSENCE))
                        .withSoilType(SoilType.exact(new ItemStack(MysticalAgradditionsContent.SPECIAL, 1, 4), null))
                        .withGrowthStageVar(MysticalAgradditionsContent.AWAKENED_DRACONIUM_CROP, BlockCrops.AGE)));
        cNeutroniumSeeds.ifEnabled(() -> handler.addEntry(
                new SimplePlantHandler.Entry(new ItemStack(MysticalAgradditionsContent.NEUTRONIUM_SEEDS),
                        new ItemStack(MysticalAgradditionsContent.NEUTRONIUM_ESSENCE))
                        .withSoilType(SoilType.exact(new ItemStack(MysticalAgradditionsContent.SPECIAL, 1, 5), null))
                        .withGrowthStageVar(MysticalAgradditionsContent.NEUTRONIUM_CROP, BlockCrops.AGE)));
        if (!handler.isEmpty()) {
            registrar.registerPlantHandler(handler);
        }
    }

}

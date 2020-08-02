package xyz.phanta.clochepp.module.agricraft;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import com.infinityraider.agricraft.api.v1.AgriApi;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v1.requirement.IGrowthRequirement;
import com.infinityraider.agricraft.api.v1.seed.AgriSeed;
import com.infinityraider.agricraft.api.v1.soil.IAgriSoil;
import com.infinityraider.agricraft.api.v1.stat.IAgriStat;
import com.infinityraider.agricraft.api.v1.util.FuzzyStack;
import com.infinityraider.agricraft.reference.AgriCraftConfig;
import com.infinityraider.agricraft.renderers.PlantRenderer;
import com.infinityraider.infinitylib.render.tessellation.ITessellator;
import com.infinityraider.infinitylib.render.tessellation.TessellatorVertexBuffer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.clochepp.CppConfig;
import xyz.phanta.clochepp.cloche.SimplePlantHandler;
import xyz.phanta.clochepp.cloche.SoilType;
import xyz.phanta.clochepp.util.FloatUtils;
import xyz.phanta.clochepp.util.RepeatCollection;

import java.util.*;

public class AgriPlantHandler implements BelljarHandler.IPlantHandler {

    private static final Random rand = new Random();

    private final int seedDropNeighborCount;
    private final Map<IAgriPlant, AgriPlantData> cachedPlantInfo = new HashMap<>();

    public AgriPlantHandler(int seedDropNeighborCount) {
        this.seedDropNeighborCount = seedDropNeighborCount;
    }

    @Override
    public boolean isCorrectSoil(ItemStack seedStack, ItemStack soilStack) {
        return lookupSeed(seedStack).map(s -> getPlantData(s).getSoilType().test(getEffSoil(soilStack))).orElse(false);
    }

    @Override
    public float getGrowthStep(ItemStack seedStack, ItemStack soilStack,
                               float growth, TileEntity tile, float fertilizer, boolean render) {
        return lookupSeed(seedStack).map(s -> getPlantData(s).computeGrowth(s.getStat(), fertilizer)).orElse(0F);
    }

    @Override
    public ItemStack[] getOutput(ItemStack seedStack, ItemStack soilStack, TileEntity tile) {
        return lookupSeed(seedStack)
                .map(s -> getPlantData(s).getDrops(s, getEffSoil(soilStack), tile))
                .orElse(SimplePlantHandler.NO_STACKS);
    }

    @Override
    public boolean isValid(ItemStack seedStack) {
        return AgriApi.getSeedRegistry().hasAdapter(seedStack);
    }

    @Override
    public IBlockState[] getRenderedPlant(ItemStack seedStack, ItemStack soilStack, float growth, TileEntity tile) {
        return SimplePlantHandler.NO_STATES;
    }

    @Override
    public boolean overrideRender(ItemStack seedStack, ItemStack soilStack, float growth, TileEntity tile,
                                  BlockRendererDispatcher renderer) {
        lookupSeed(seedStack).ifPresent(s ->
                getPlantData(s).render(growth, getRenderSize(seedStack, soilStack, growth, tile)));
        return true;
    }

    private static Optional<AgriSeed> lookupSeed(ItemStack seedStack) {
        return AgriApi.getSeedRegistry().getAdapter(seedStack).flatMap(a -> a.valueOf(seedStack));
    }

    private AgriPlantData getPlantData(AgriSeed seed) {
        return cachedPlantInfo.computeIfAbsent(seed.getPlant(), AgriPlantData::new);
    }

    private static ItemStack getEffSoil(ItemStack soilStack) {
        return SoilType.DIRT.test(soilStack) ? new ItemStack(Blocks.FARMLAND) : soilStack;
    }

    private class AgriPlantData {

        private final IAgriPlant plant;
        private final double growthChanceBase, growthChanceAdditional;
        private final int growthStages;
        private final double spreadChance;
        private final SoilType soilType;

        @SuppressWarnings("deprecation")
        public AgriPlantData(IAgriPlant plant) {
            this.plant = plant;
            this.growthChanceBase = plant.getGrowthChanceBase();
            this.growthChanceAdditional = plant.getGrowthChanceBonus();
            this.growthStages = plant.getGrowthStages();
            this.spreadChance = plant.getSpreadChance();
            IGrowthRequirement reqs = plant.getGrowthRequirement();
            FuzzyStack cond = reqs.getConditionStack().orElse(null);
            if (cond != null) {
                this.soilType = new SoilType(s -> FuzzyStack.from(s).map(cond::equals).orElse(false), null);
            } else {
                Collection<IAgriSoil> soils = reqs.getSoils();
                this.soilType = new SoilType(s -> soils.stream().anyMatch(t -> t.isVarient(s)), null);
            }
        }

        public SoilType getSoilType() {
            return soilType;
        }

        public float computeGrowth(IAgriStat seedStats, float fertilizer) {
            // normally, agricraft growth is a stochastic process with a `p` probability of growth each attempt
            // expected number of growth steps is thus `1/p`
            // expected total number of steps to complete `q` growth stages is thus `q/p`
            // so we advance `p/q` growth per growth step to exactly match the expectation (minus truncation)
            // divide by 40 based on vanilla crops having 8 growth stages and requiring 320 ticks to grow in a cloche
            return (float)((growthChanceBase + growthChanceAdditional * seedStats.getGrowth())
                    * AgriCraftConfig.growthMultiplier / growthStages) * fertilizer / 40F
                    * (float)CppConfig.agriCraftGrowthRateModifier;
        }

        public ItemStack[] getDrops(AgriSeed seed, ItemStack soilStack, TileEntity tile) {
            List<ItemStack> drops = new ArrayList<>();
            SimulatedAgriCrop crop = new SimulatedAgriCrop(tile, seed, soilStack);
            IAgriStat stats = seed.getStat();
            for (int i = (stats.getGain() + 3) / 3; i > 0; i--) {
                plant.getHarvestProducts(drops::add, crop, stats, rand);
            }
            if (seedDropNeighborCount > 0 && rand.nextDouble() < spreadChance) {
                if (seedDropNeighborCount == 1) {
                    drops.add(seed.toStack());
                } else {
                    AgriApi.getStatCalculatorRegistry().valueOf(plant)
                            .map(c -> new AgriSeed(plant,
                                    c.calculateSpreadStats(plant, new RepeatCollection<>(crop, seedDropNeighborCount))))
                            .ifPresent(s -> drops.add(s.toStack()));
                }
            }
            return drops.toArray(SimplePlantHandler.NO_STACKS);
        }

        @SideOnly(Side.CLIENT)
        public void render(float growth, float scale) {
            float scaleInv = 0.0625F / scale;
            GlStateManager.disableAlpha(); // cached alpha test state is sometimes invalid
            GlStateManager.enableAlpha();
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(scaleInv, 0F, scaleInv);
            ITessellator tess = TessellatorVertexBuffer.getInstance();
            tess.startDrawingQuads(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            PlantRenderer.renderPlant(tess, plant, FloatUtils.discretize(growth, growthStages));
            tess.draw();
            GlStateManager.popMatrix();
        }

    }

}

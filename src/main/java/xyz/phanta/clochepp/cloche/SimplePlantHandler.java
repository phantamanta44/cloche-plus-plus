package xyz.phanta.clochepp.cloche;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.clochepp.util.FloatUtils;
import xyz.phanta.clochepp.util.ComparableItemMap;

import javax.annotation.Nullable;

public class SimplePlantHandler implements BelljarHandler.IPlantHandler {

    public static final ItemStack[] NO_STACKS = new ItemStack[0];
    public static final IBlockState[] NO_STATES = new IBlockState[0];

    private final ComparableItemMap<Entry> entries;

    public SimplePlantHandler(boolean compareOreDict, boolean compareNbt) {
        this.entries = new ComparableItemMap<>(compareOreDict, compareNbt);
    }

    public void addEntry(Entry entry) {
        entries.put(entry.seedStack, entry);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean isCorrectSoil(ItemStack seedStack, ItemStack soilStack) {
        return entries.lookup(seedStack).map(e -> e.soilType.test(soilStack)).orElse(false);
    }

    @Override
    public float getGrowthStep(ItemStack seedStack, ItemStack soilStack,
                               float growth, TileEntity tile, float fertilizer, boolean render) {
        return entries.lookup(seedStack).map(e -> fertilizer / e.growthTicks).orElse(0F);
    }

    @Override
    public ItemStack[] getOutput(ItemStack seedStack, ItemStack soilStack, TileEntity tile) {
        return entries.lookup(seedStack).map(e -> e.outputStacks).orElse(NO_STACKS);
    }

    @Override
    public boolean isValid(ItemStack seedStack) {
        return entries.containsKey(seedStack);
    }

    @Override
    public IBlockState[] getRenderedPlant(ItemStack seedStack, ItemStack soilStack, float growth, TileEntity tile) {
        return entries.lookup(seedStack).map(e -> FloatUtils.discretize(growth, e.growthStages)).orElse(NO_STATES);
    }

    @Nullable
    @Override
    public ResourceLocation getSoilTexture(ItemStack seedStack, ItemStack soilStack, TileEntity tile) {
        return entries.lookup(seedStack).map(e -> e.soilType.getDisplay()).orElse(null);
    }

    public static class Entry {

        private final ItemStack seedStack;
        private final ItemStack[] outputStacks;

        private SoilType soilType = SoilType.DIRT;
        private int growthTicks = 320;
        private IBlockState[][] growthStages = new IBlockState[0][];

        public Entry(ItemStack seedStack, ItemStack... outputStacks) {
            this.seedStack = seedStack;
            this.outputStacks = outputStacks;
        }

        public Entry withSoilType(SoilType type) {
            this.soilType = type;
            return this;
        }

        public Entry withGrowthTicks(int ticks) {
            this.growthTicks = ticks;
            return this;
        }

        public Entry withGrowthStages(IBlockState... stages) {
            this.growthStages = new IBlockState[stages.length][];
            for (int i = 0; i < growthStages.length; i++) {
                growthStages[i] = new IBlockState[] { stages[i] };
            }
            return this;
        }

        public Entry withGrowthStageVar(Block block, IProperty<Integer> prop) {
            int maxAge = prop.getAllowedValues().stream().mapToInt(i -> i).max()
                    .orElseThrow(() -> new IllegalStateException("Bad property: " + prop));
            IBlockState defaultState = block.getDefaultState();
            IBlockState[] states = new IBlockState[maxAge + 1];
            for (int i = 0; i < states.length; i++) {
                states[i] = defaultState.withProperty(prop, i);
            }
            return withGrowthStages(states);
        }

    }

}

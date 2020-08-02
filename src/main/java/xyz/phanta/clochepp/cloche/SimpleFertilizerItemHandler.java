package xyz.phanta.clochepp.cloche;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import xyz.phanta.clochepp.util.ComparableItemMap;
import xyz.phanta.clochepp.util.FloatSupplier;

public class SimpleFertilizerItemHandler implements BelljarHandler.ItemFertilizerHandler {

    private final ComparableItemMap<Entry> entries;

    public SimpleFertilizerItemHandler(boolean compareOreDict, boolean compareNbt) {
        this.entries = new ComparableItemMap<>(compareOreDict, compareNbt);
    }

    public void addEntry(Entry entry) {
        entries.put(entry.stack, entry);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean isValid(ItemStack fertilizerStack) {
        return entries.containsKey(fertilizerStack);
    }

    @Override
    public float getGrowthMultiplier(ItemStack fertilizerStack, ItemStack seedStack, ItemStack soilStack, TileEntity tile) {
        return entries.lookup(fertilizerStack).map(Entry::getMultiplier).orElse(1F);
    }

    public static class Entry {

        private final ItemStack stack;
        private final FloatSupplier multiplierGetter;

        public Entry(ItemStack stack, FloatSupplier multiplierGetter) {
            this.stack = stack;
            this.multiplierGetter = multiplierGetter;
        }

        public float getMultiplier() {
            return multiplierGetter.get();
        }

    }

}

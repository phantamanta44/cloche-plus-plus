package xyz.phanta.clochepp.cloche;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import xyz.phanta.clochepp.util.ComparableItemMap;

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
        return entries.lookup(fertilizerStack).map(e -> e.multiplier).orElse(1F);
    }

    public static class Entry {

        private final ItemStack stack;
        private final float multiplier;

        public Entry(ItemStack stack, float multiplier) {
            this.stack = stack;
            this.multiplier = multiplier;
        }

    }

}

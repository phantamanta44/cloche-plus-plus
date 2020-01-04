package xyz.phanta.clochepp.util;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ComparableItemMap<T> {

    private final Map<ComparableItemStack, T> backingMap;
    private final boolean compareOreDict, compareNbt;

    public ComparableItemMap(boolean compareOreDict, boolean compareNbt) {
        this(new HashMap<>(), compareOreDict, compareNbt);
    }

    public ComparableItemMap(Map<ComparableItemStack, T> backingMap, boolean compareOreDict, boolean compareNbt) {
        this.backingMap = backingMap;
        this.compareOreDict = compareOreDict;
        this.compareNbt = compareNbt;
    }

    public void put(ItemStack stack, T value) {
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Stack cannot be empty!");
        }
        backingMap.put(getKey(stack), value);
    }

    @Nullable
    public T get(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return backingMap.get(getKey(stack));
    }

    public Optional<T> lookup(ItemStack stack) {
        return Optional.ofNullable(get(stack));
    }

    public boolean containsKey(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return backingMap.containsKey(getKey(stack));
    }

    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    private ComparableItemStack getKey(ItemStack stack) {
        ComparableItemStack key = new ComparableItemStack(stack, compareOreDict);
        key.setUseNBT(compareNbt);
        return key;
    }

}

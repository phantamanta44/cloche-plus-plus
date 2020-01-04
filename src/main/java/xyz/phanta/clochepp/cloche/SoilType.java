package xyz.phanta.clochepp.cloche;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Predicate;

public class SoilType {

    public static final SoilType DIRT = oreDict("dirt", null);
    public static final SoilType SAND = oreDict("sand", null);

    public static SoilType oreDict(String key, @Nullable IBlockState display) {
        int targetId = OreDictionary.getOreID(key);
        return new SoilType(s -> {
            for (int id : OreDictionary.getOreIDs(s)) {
                if (id == targetId) {
                    return true;
                }
            }
            return false;
        }, display);
    }

    public static SoilType exact(ItemStack stack, @Nullable IBlockState display) {
        return new SoilType(s -> ItemHandlerHelper.canItemStacksStack(stack, s), display);
    }

    private final Predicate<ItemStack> matcher;

    @Nullable
    private final IBlockState display;

    public SoilType(Predicate<ItemStack> matcher, @Nullable IBlockState display) {
        this.matcher = matcher;
        this.display = display;
    }

    public boolean test(ItemStack stack) {
        return !stack.isEmpty() && matcher.test(stack);
    }

    @Nullable
    public ResourceLocation getDisplay() {
        if (display != null) {
            Collection<ResourceLocation> textures = Minecraft.getMinecraft().getBlockRendererDispatcher()
                    .getModelForState(display).getParticleTexture().getDependencies();
            if (!textures.isEmpty()) {
                return textures.iterator().next();
            }
        }
        return null;
    }

    public SoilType or(SoilType... other) {
        Predicate<ItemStack> m = matcher;
        for (SoilType type : other) {
            m = m.or(type.matcher);
        }
        return new SoilType(m, null);
    }

}

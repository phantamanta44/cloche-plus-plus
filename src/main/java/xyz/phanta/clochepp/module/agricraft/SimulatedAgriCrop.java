package xyz.phanta.clochepp.module.agricraft;

import com.infinityraider.agricraft.api.v1.AgriApi;
import com.infinityraider.agricraft.api.v1.crop.IAgriCrop;
import com.infinityraider.agricraft.api.v1.fertilizer.IAgriFertilizer;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v1.seed.AgriSeed;
import com.infinityraider.agricraft.api.v1.soil.IAgriSoil;
import com.infinityraider.agricraft.api.v1.util.MethodResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class SimulatedAgriCrop implements IAgriCrop {

    private final TileEntity tile;
    private final AgriSeed seed;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<IAgriSoil> soil;

    public SimulatedAgriCrop(TileEntity tile, AgriSeed seed, ItemStack soilStack) {
        this.tile = tile;
        this.seed = seed;
        this.soil = AgriApi.getSoilRegistry().get(soilStack);
    }

    @Override
    public BlockPos getCropPos() {
        return tile.getPos();
    }

    @Override
    public World getCropWorld() {
        return tile.getWorld();
    }

    @Override
    public int getGrowthStage() {
        return seed.getPlant().getGrowthStages() - 1;
    }

    @Override
    public boolean setGrowthStage(int stage) {
        return false;
    }

    @Override
    public boolean isCrossCrop() {
        return false;
    }

    @Override
    public boolean setCrossCrop(boolean crossCrop) {
        return false;
    }

    @Override
    public boolean isFertile(@Nullable IAgriPlant plant) {
        return true;
    }

    @Override
    public boolean isMature() {
        return true;
    }

    @Override
    public Optional<IAgriSoil> getSoil() {
        return soil;
    }

    @Override
    public MethodResult onGrowthTick() {
        return MethodResult.PASS;
    }

    @Override
    public MethodResult onApplyCrops(@Nullable EntityPlayer player) {
        return MethodResult.PASS;
    }

    @Override
    public MethodResult onApplySeeds(AgriSeed seed, @Nullable EntityPlayer player) {
        return MethodResult.PASS;
    }

    @Override
    public MethodResult onBroken(Consumer<ItemStack> dropCollector, @Nullable EntityPlayer player) {
        return MethodResult.PASS;
    }

    @Override
    public boolean acceptsFertilizer(@Nullable IAgriFertilizer fertilizer) {
        return false;
    }

    @Override
    public MethodResult onApplyFertilizer(@Nullable IAgriFertilizer fertilizer, Random rand) {
        return MethodResult.PASS;
    }

    @Override
    public MethodResult onHarvest(Consumer<ItemStack> dropCollector, @Nullable EntityPlayer player) {
        return MethodResult.PASS;
    }

    @Override
    public boolean onRaked(Consumer<ItemStack> dropCollector, @Nullable EntityPlayer player) {
        return false;
    }

    @Override
    public boolean acceptsSeed(@Nullable AgriSeed seed) {
        return false;
    }

    @Override
    public boolean setSeed(@Nullable AgriSeed seed) {
        return false;
    }

    @Override
    public boolean hasSeed() {
        return true;
    }

    @Nullable
    @Override
    public AgriSeed getSeed() {
        return seed;
    }

}

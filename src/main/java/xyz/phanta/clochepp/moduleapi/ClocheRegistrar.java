package xyz.phanta.clochepp.moduleapi;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;

public interface ClocheRegistrar {

    void registerPlantHandler(BelljarHandler.IPlantHandler handler);

    void registerFertilizerItemHandler(BelljarHandler.ItemFertilizerHandler handler);

    void registerFertilizerFluidHandler(BelljarHandler.FluidFertilizerHandler handler);

}

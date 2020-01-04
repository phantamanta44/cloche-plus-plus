package xyz.phanta.clochepp.util;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;

import java.lang.reflect.Field;
import java.util.HashSet;

public class ImmEngReflect {

    private static final Field fBelljarHandler_plantHandlers;

    static {
        try {
            fBelljarHandler_plantHandlers = BelljarHandler.class.getDeclaredField("plantHandlers");
            fBelljarHandler_plantHandlers.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalStateException("IE reflection setup failed!", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static HashSet<BelljarHandler.IPlantHandler> getPlantHandlers() {
        try {
            return (HashSet<BelljarHandler.IPlantHandler>)fBelljarHandler_plantHandlers.get(null);
        } catch (Exception e) {
            throw new IllegalStateException("IE reflection failed!", e);
        }
    }

    public static void setPlantHandlers(HashSet<BelljarHandler.IPlantHandler> plantHandlers) {
        try {
            fBelljarHandler_plantHandlers.set(null, plantHandlers);
        } catch (Exception e) {
            throw new IllegalStateException("IE reflection failed!", e);
        }
    }

}

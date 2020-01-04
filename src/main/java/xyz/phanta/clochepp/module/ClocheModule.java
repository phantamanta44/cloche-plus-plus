package xyz.phanta.clochepp.module;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.clochepp.moduleapi.ClocheRegistrar;
import xyz.phanta.clochepp.moduleapi.ComponentRegistrar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ClocheModule {

    default void init() {
        // NO-OP
    }

    @SideOnly(Side.CLIENT)
    default void initClient() {
        // NO-OP
    }

    void registerComponents(ComponentRegistrar registrar);

    void register(ClocheRegistrar registrar);

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Register {

        String name();

        String id();

        String[] deps() default {};

    }

}

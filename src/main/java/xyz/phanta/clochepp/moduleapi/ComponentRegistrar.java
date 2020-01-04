package xyz.phanta.clochepp.moduleapi;

public interface ComponentRegistrar {

    ClocheComponent registerComponent(String name, String... deps);

}

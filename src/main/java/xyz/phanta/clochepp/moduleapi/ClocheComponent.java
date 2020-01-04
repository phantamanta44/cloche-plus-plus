package xyz.phanta.clochepp.moduleapi;

public interface ClocheComponent {

    boolean isEnabled();

    default void ifEnabled(Runnable action) {
        if (isEnabled()) {
            action.run();
        }
    }

}

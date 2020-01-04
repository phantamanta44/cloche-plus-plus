package xyz.phanta.clochepp.util;

public class FloatUtils {

    public static int discretize(float value, int codomainBound) {
        return Math.min((int)Math.floor(value * codomainBound), codomainBound - 1);
    }

    public static <T> T discretize(float value, T[] codomain) {
        return codomain[discretize(value, codomain.length)];
    }

}

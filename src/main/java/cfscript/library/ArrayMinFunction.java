package cfscript.library;

import java.util.Arrays;

public class ArrayMinFunction {
    public static double ArrayMin(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        return Arrays.stream(array).min().getAsDouble();
    }
}
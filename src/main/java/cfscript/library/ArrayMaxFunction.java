package cfscript.library;

import java.util.Arrays;

public class ArrayMaxFunction {
    public static double ArrayMax(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        return Arrays.stream(array).max().getAsDouble();
    }
}
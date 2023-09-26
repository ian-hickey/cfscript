package cfscript.library;

import java.util.Arrays;
public class ArrayResizeFunction {
    public static <T> T[] ArrayResize(T[] array, int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        return Arrays.copyOf(array, size);
    }
}
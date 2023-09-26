package cfscript.library;

import java.util.Arrays;

public class ArraySortFunction {
    public static <T extends Comparable<T>> T[] ArraySort(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        Arrays.sort(array);
        return array;
    }
}
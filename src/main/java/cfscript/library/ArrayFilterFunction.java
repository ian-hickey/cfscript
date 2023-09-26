package cfscript.library;

import java.util.function.Predicate;
import java.util.Arrays;

public class ArrayFilterFunction {
    public static <T> T[] ArrayFilter(T[] array, Predicate<T> predicate) {
        return Arrays.stream(array)
            .filter(predicate)
            .toArray(size -> Arrays.copyOf(array, size));
    }
}
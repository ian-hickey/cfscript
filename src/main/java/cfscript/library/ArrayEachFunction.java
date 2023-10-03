package cfscript.library;

import java.util.function.Function;

public class ArrayEachFunction {
    public static <T> void ArrayEach(T[] array, Function<T, Void> function) {
        for (T item : array) {
            function.apply(item);
        }
    }
}
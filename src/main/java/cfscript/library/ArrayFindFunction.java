package cfscript.library;

import java.util.function.Predicate;

public class ArrayFindFunction {
    public static <T> int ArrayFind(T[] array, Predicate<T> predicate) {
        for (int i = 0; i < array.length; i++) {
            if (predicate.test(array[i])) {
                return i + 1;
            }
        }
        return 0;
    }
}
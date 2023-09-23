package cfscript.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToListFunction {
    public static <T> List<T> ArrayToList(T[] array) {
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(array);
    }
}
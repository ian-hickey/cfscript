package cfscript.library;

import java.util.List;

public class ListAppendFunction {
    public static <T> void ListAppend(List<T> list, T item) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        list.add(item);
    }
}
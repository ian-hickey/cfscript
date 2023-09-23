package cfscript.library;

import java.util.List;

public class ListPrependFunction {
    public static <T> void ListPrepend(List<T> list, T item) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        list.add(0, item);
    }
}
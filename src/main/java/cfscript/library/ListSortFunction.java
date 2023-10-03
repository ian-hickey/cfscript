package cfscript.library;

import java.util.Collections;
import java.util.List;

public class ListSortFunction {
    public static <T extends Comparable<T>> void ListSort(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        Collections.sort(list);
    }
}
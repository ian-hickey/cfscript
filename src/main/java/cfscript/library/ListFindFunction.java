package cfscript.library;

import java.util.List;

public class ListFindFunction {
    public static <T> int ListFind(List<T> list, T item) {
        if (list == null) {
            return -1;
        }
        int index = list.indexOf(item);
        return index == -1 ? -1 : index + 1;
    }
}
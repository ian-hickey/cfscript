package cfscript.library;

import java.util.Arrays;
public class ArrayInsertAtFunction {
    public static <T> T[] ArrayInsertAt(T[] array, int index, T item) {
        if (index < 1 || index > array.length + 1) {
            throw new IndexOutOfBoundsException();
        }
        T[] newArray = Arrays.copyOf(array, array.length + 1);
        System.arraycopy(array, index - 1, newArray, index, array.length - index + 1);
        newArray[index - 1] = item;
        return newArray;
    }
}
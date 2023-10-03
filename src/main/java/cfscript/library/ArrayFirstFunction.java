package cfscript.library;

public class ArrayFirstFunction {
    public static <T> T ArrayFirst(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        return array[0];
    }
}
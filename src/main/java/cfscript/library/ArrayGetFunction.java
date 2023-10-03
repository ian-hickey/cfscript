package cfscript.library;

public class ArrayGetFunction {
    public static <T> T ArrayGet(T[] array, int index) {
        if (index < 1 || index > array.length) {
            throw new IndexOutOfBoundsException();
        }
        return array[index - 1];
    }
}
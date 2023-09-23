package cfscript.library;

public class ArraySetFunction {
    public static <T> void ArraySet(T[] array, int index, T value) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (index < 1 || index > array.length) {
            throw new IndexOutOfBoundsException();
        }
        array[index - 1] = value;
    }
}
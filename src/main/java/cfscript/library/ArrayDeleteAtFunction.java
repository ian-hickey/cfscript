package cfscript.library;

public class ArrayDeleteAtFunction {
    public static Object[] ArrayDeleteAt(Object[] array, int index) {
        if (index < 1 || index > array.length) {
            throw new IndexOutOfBoundsException();
        }
        Object[] newArray = new Object[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index - 1);
        System.arraycopy(array, index, newArray, index - 1, array.length - index);
        return newArray;
    }
}
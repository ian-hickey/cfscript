package cfscript.library;

public class ArrayDeleteFunction {
    public static Object[] ArrayDelete(Object[] array, Object item) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return array;
        }
        Object[] newArray = new Object[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }
}
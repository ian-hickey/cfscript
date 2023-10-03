package cfscript.library;

public class ArrayIsEmptyFunction {
    public static <T> boolean ArrayIsEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
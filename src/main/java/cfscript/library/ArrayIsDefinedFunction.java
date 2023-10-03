package cfscript.library;

public class ArrayIsDefinedFunction {
    public static <T> boolean ArrayIsDefined(T[] array, int index) {
        return array != null && index > 0 && index <= array.length;
    }
}
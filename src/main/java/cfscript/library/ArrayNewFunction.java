package cfscript.library;

public class ArrayNewFunction {
    public static <T> T[] ArrayNew(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        return (T[]) new Object[size];
    }
}
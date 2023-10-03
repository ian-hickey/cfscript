package cfscript.library;

public class ArrayContainsNoCaseFunction {
    public static int ArrayContainsNoCase(String[] array, String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(item)) {
                return i + 1;
            }
        }
        return 0;
    }
}
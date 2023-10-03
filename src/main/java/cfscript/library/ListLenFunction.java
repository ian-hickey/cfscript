package cfscript.library;

public class ListLenFunction {
    public static int listLen(String list, String delimiter) {
        if (delimiter == null || delimiter.isEmpty()) {
            delimiter = ",";
        }
        return list.split(delimiter).length;
    }
}
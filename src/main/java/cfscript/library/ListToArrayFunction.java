package cfscript.library;

public class ListToArrayFunction {
    // This method will split a ColdFusion list (a string with delimited values) into an array of strings.
    public static String[] ListToArray(String list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return new String[0];  // Return an empty array if the list is null or empty.
        }
        if (delimiter == null || delimiter.isEmpty()){
            delimiter = ",";
        }
        return list.split(delimiter);  // Split the list string into an array using a comma as the delimiter.
    }
}
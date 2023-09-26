package cfscript.library;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFunction {

    public static String DateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
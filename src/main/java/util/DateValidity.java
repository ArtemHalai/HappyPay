package util;

import java.sql.Date;

public class DateValidity {
    public static boolean valid(Date date) {
        return date.getTime() > System.currentTimeMillis();
    }
}

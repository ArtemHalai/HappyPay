package util;

import java.sql.Date;
import java.time.LocalDate;

public class SqlDateLocalDateConverter {

    public static Date convertLocalDateToSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate convertSqlDateToLocalDate(Date date) {
        return date.toLocalDate();
    }
}

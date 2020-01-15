package util;

import java.sql.Date;
import java.time.LocalDate;

public class DateValidity {

    private static final long FIVE_YEARS = 5;
    private static final long ONE_YEAR = 31556952000L;

    public static boolean valid(LocalDate date) {
       return date.toEpochDay() > LocalDate.now().toEpochDay();
    }

    public static LocalDate getValidity() {
        return LocalDate.now().plusYears(FIVE_YEARS);
    }

    public static Date setDepositTerm() {
        return new Date(System.currentTimeMillis() + ONE_YEAR);
    }
}

package util;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateValidity {

    private static final long FIVE_YEARS = 5;
    private static final long ONE_YEAR = 31556952000L;

    public static boolean valid(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                > LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getValidity() {
        return LocalDateTime.now().plusYears(FIVE_YEARS);
    }

    public static Date setDepositTerm() {
        return new Date(System.currentTimeMillis() + ONE_YEAR);
    }
}

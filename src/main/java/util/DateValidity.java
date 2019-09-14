package util;

import java.sql.Date;

public class DateValidity {

    private static final long FIVE_YEARS = 157680000000L;
    private static final long ONE_YEAR = 31556952000L;
    public static boolean valid(Date date) {
        return date.getTime() > System.currentTimeMillis();
    }

    public static Date setValidity(){
       return new Date(System.currentTimeMillis()+FIVE_YEARS);
    }

    public static Date setDepositTerm(){
       return new Date(System.currentTimeMillis()+ONE_YEAR);
    }
}

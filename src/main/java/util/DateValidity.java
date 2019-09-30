package util;

import java.sql.Date;

/**
 * Define a class to check the date validity.
 */
public class DateValidity {

    private static final long FIVE_YEARS = 157680000000L;
    private static final long ONE_YEAR = 31556952000L;

    /**
     * Method to validate the date.
     *
     * @param date The sql.Date object.
     * @return <code>true</code> if given date is more than current date; <code>false</code> otherwise.
     * @see Date
     */
    public static boolean valid(Date date) {
        return date.getTime() > System.currentTimeMillis();
    }

    /**
     * This is a setter which sets the validity date.
     *
     * @return The sql.Date which has current date plus 5 years.
     */
    public static Date setValidity(){
       return new Date(System.currentTimeMillis()+FIVE_YEARS);
    }

    /**
     * This is a setter which sets the deposit term date.
     *
     * @return The sql.Date which has current date plus 1 year.
     */
    public static Date setDepositTerm(){
       return new Date(System.currentTimeMillis()+ONE_YEAR);
    }
}

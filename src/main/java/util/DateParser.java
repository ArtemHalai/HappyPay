package util;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Define a class to parse the date.
 */
public class DateParser {

    private static final Logger LOG = Logger.getLogger(DateParser.class);

    /**
     * Method to check user's role and id.
     *
     * @param date The string value of date.
     * @return The sql.Date object.
     */
    public static Date parse(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = new Date(System.currentTimeMillis());
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
           LOG.error("Parse exception occurred in DateParser.class at parse() method");
        }
        return new Date(d.getTime());
    }
}

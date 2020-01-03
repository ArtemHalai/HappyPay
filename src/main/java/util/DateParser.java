package util;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParser {

    private static final Logger log = Logger.getLogger(DateParser.class);

    public static Date parse(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = new Date(System.currentTimeMillis());
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
           log.error("Parse exception occurred in DateParser.class at parse() method");
        }
        return new Date(d.getTime());
    }
}

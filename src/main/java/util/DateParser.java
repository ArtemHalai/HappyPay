package util;

import lombok.extern.log4j.Log4j;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Log4j
public class DateParser {

    private DateParser() {

    }

    public static Date parse(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = new Date(System.currentTimeMillis());
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            log.error("Parse exception occurred in DateParser.class at parse() method", e);
        }
        return new Date(d.getTime());
    }
}

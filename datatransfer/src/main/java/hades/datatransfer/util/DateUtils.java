package hades.datatransfer.util;

import java.text.ParseException;
import java.util.Date;

public class DateUtils {

    public static final String YMD = "yyyy-MM-dd";

    private static final String[] datePatterns = {};

    public static boolean isDate(String str) {
        Date date = null;
        for (String datePattern : datePatterns) {
            try {
                date = org.apache.commons.lang3.time.DateUtils.parseDate(str, datePattern);
                break;
            } catch (ParseException e) {
            }
        }
        return date == null ? false : true;
    }
}

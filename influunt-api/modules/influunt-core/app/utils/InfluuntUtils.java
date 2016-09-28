package utils;

import com.google.common.base.CaseFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by lesiopinheiro on 7/14/16.
 */
public class InfluuntUtils {

    // Deafult Format - dd/mm/yyyy HH:MM:SS
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private static final String DEFAULT_DATE_REGEX = "(\\d{2})/(\\d{2})/(\\d{4})[ ](\\d{2})[:](\\d{2})[:](\\d{2})";

    private static final InfluuntUtils instance = new InfluuntUtils();

    protected InfluuntUtils() {
    }

    public static InfluuntUtils getInstance() {
        return instance;
    }

    /**
     * Check the {@link String} is a valid date and then returns the formatted value
     *
     * @param value  to be formatted
     * @param format format used to format the value
     * @return {@link DateTime}
     */
    public static DateTime parseDate(String value, String format) {
        if (format == null || format.isEmpty()) {
            format = DEFAULT_DATE_REGEX;
        }

        if (value.matches(format)) {
            return DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT).parseDateTime(value);
        }

        return null;
    }

    public static String underscore(String input) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
    }

    public boolean multiplo(Integer x, Integer y) {
        if (x == null || y == null || x == 0 || y == 0) {
            return false;
        }
        if (x.compareTo(y) == 1) {
            return x % y == 0;
        }
        return y % x == 0;
    }

    public static String formatDateToString(DateTime date, String format) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(format);
        return dtfOut.print(date);
    }
}

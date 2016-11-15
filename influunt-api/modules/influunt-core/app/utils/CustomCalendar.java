package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rodrigosol on 9/20/16.
 */
public class CustomCalendar {
    private static final int INITIAL_YEAR = 2016;

    private static final int INITIAL_MONTH = 8;

    private static final int INITIAL_DAY = 18;

    private static final int INITIAL_HOUR = 0;

    private static final int INITIAL_MINUTE = 0;

    private static final int INITIAL_SECOND = 0;

    public static Calendar getCalendar() {
        Calendar calendar = new GregorianCalendar(INITIAL_YEAR, INITIAL_MONTH, INITIAL_DAY,
            INITIAL_HOUR, INITIAL_MINUTE, INITIAL_SECOND);
        return calendar;
    }

    public static int getTempoAjustado(Calendar meuCalendar) {
        return (meuCalendar.get(Calendar.HOUR_OF_DAY) * 60 * 60) + (meuCalendar.get(Calendar.MINUTE) * 60) + (meuCalendar.get(Calendar.SECOND) * 60);
    }
}

package formmaters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Singleton
public class FormattersProvider implements Provider<Formatters> {

    public static final String STR_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(STR_DATE_TIME_FORMAT);

    private final MessagesApi messagesApi;

    @Inject
    public FormattersProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Formatters get() {
        Formatters formatters = new Formatters(messagesApi);

        formatters.register(Date.class, new SimpleFormatter<Date>() {


            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                Long millis = Long.valueOf(text);
                return new Date(millis);
            }

            @Override
            public String print(Date date, Locale locale) {
                return DATE_FORMAT.format(date);
            }
        });


        formatters.register(DateTime.class, new SimpleFormatter<DateTime>() {


            @Override
            public DateTime parse(String text, Locale locale) throws ParseException {
                return DateTime.parse(text, DateTimeFormat.forPattern(STR_DATE_TIME_FORMAT));
            }

            @Override
            public String print(DateTime date, Locale locale) {
                return DATE_FORMAT.format(date);
            }
        });

        return formatters;
    }
}
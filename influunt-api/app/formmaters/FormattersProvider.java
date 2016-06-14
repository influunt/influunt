package formmaters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.joda.time.LocalTime;

import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.i18n.MessagesApi;


@Singleton
public class FormattersProvider implements Provider<Formatters> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    private static final SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat("yyyy-MM-dd");

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

        return formatters;
    }
}
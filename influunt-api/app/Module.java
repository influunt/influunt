
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import formmaters.FormattersProvider;
import play.data.format.Formatters;
import security.Authenticator;
import security.DumbAuthenticator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class is a Guice module that tells Guice how to bind several different
 * types. This Guice module is created when the Play application starts.
 *
 * Play will automatically use any class called `Module` that is in the root
 * package. You can create modules in other locations by adding
 * `play.modules.enabled` settings to the `application.conf` configuration file.
 */

public class Module extends AbstractModule {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    private static final SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void configure() {

        bind(Authenticator.class).to(DumbAuthenticator.class).in(Singleton.class);
    }




}

package os72c.client.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

/**
 * Created by rodrigosol on 11/30/16.
 */
public class InfluuntLogger {
    public static Logger logger = (Logger) LoggerFactory.getLogger("Influunt");
}

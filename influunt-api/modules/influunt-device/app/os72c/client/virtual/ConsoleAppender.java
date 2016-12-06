package os72c.client.virtual;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Context;

import javax.swing.*;

/**
 * A Logback appender that appends messages to a {@link JTextArea}.
 *
 * @author David Tombs
 */
public class ConsoleAppender extends AppenderBase<ILoggingEvent> {

    private final PatternLayout fPatternLayout;

    public ConsoleAppender(final Context loggerContext) {

        // Log the date, level, class name (no package), and the message.
        fPatternLayout = new PatternLayout();
        fPatternLayout.setPattern("%d{HH:mm:ss.SSS} %-5level - %msg");
        fPatternLayout.setContext(loggerContext);
        fPatternLayout.start();

        // Make sure not to call any subclass methods right now.
        super.setContext(loggerContext);

    }

    @Override
    protected void append(final ILoggingEvent eventObject) {
        System.out.println(fPatternLayout.doLayout(eventObject));
    }
}

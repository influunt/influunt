package logger;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.google.common.collect.EvictingQueue;

/**
 * Created by rodrigosol on 12/1/16.
 */
public class InfluuntLogAppender extends RollingFileAppender<ILoggingEvent> {

    public static EvictingQueue<String> evictingQueue;

    private final PatternLayout fPatternLayout;

    public InfluuntLogAppender(Context context, String path, String name, int size) {
        evictingQueue = EvictingQueue.create(30);

        // Log the date, level, class name (no package), and the message.
        fPatternLayout = new PatternLayout();
        fPatternLayout.setPattern("%d{HH:mm:ss.SSS} - %msg");
        fPatternLayout.setContext(context);
        fPatternLayout.start();

        setContext(context);
        setFile(path.concat("/").concat(name));
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(getContext());
        encoder.setPattern("%d{dd/MM/yyyy HH:mm:ss.SSS} %msg%n");
        setEncoder(encoder);
        encoder.start();

        SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = new SizeBasedTriggeringPolicy();
        sizeBasedTriggeringPolicy.setMaxFileSize((size / 5) + "kb");
        sizeBasedTriggeringPolicy.setContext(getContext());
        setTriggeringPolicy(sizeBasedTriggeringPolicy);
        sizeBasedTriggeringPolicy.start();

        TimeBasedRollingPolicy tracePolicy = new TimeBasedRollingPolicy();
        tracePolicy.setContext(getContext());
        tracePolicy.setFileNamePattern(name + "-%d{yyyy-MM-dd}.log");
        tracePolicy.setMaxHistory(5);
        tracePolicy.setParent(this);
        tracePolicy.start();
        setRollingPolicy(tracePolicy);


        start();

    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        super.append(eventObject);
        evictingQueue.add(fPatternLayout.doLayout(eventObject));
    }

}

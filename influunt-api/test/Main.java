package logbacktesting;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {

        Logger templateLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.myapp");
        LoggerContext loggerContext = templateLogger.getLoggerContext();

        String[] nameList = new String[] {"test1.class", "test2.class"};

        // Set up the pattern
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        // Spin over the names to create all the needed objects
        for(int i = 0; i < nameList.length; i++) {

            String name = nameList[i];

            // Set up the roll over policies and the name when it rolls over
            TimeBasedRollingPolicy tracePolicy = new TimeBasedRollingPolicy();
            tracePolicy.setContext( loggerContext );
            tracePolicy.setFileNamePattern( name + "-Trace-%d{yyyy-MM-dd}.logger" );
            tracePolicy.setMaxHistory( 30 );

            TimeBasedRollingPolicy errorPolicy = new TimeBasedRollingPolicy();
            errorPolicy.setContext( loggerContext );
            errorPolicy.setFileNamePattern( name + "-Error-logFile.%d{yyyy-MM-dd}.logger" );
            errorPolicy.setMaxHistory( 30 );

            // Set up the filters to ensure things get split as expected
            LevelFilter traceFilter = new LevelFilter();
            traceFilter.setContext( loggerContext );
            traceFilter.setLevel( Level.TRACE );
            traceFilter.setOnMatch( FilterReply.ACCEPT );
            traceFilter.setOnMismatch( FilterReply.DENY );

            LevelFilter errorFilter = new LevelFilter();
            errorFilter.setContext( loggerContext );
            errorFilter.setLevel( Level.ERROR );
            errorFilter.setOnMatch( FilterReply.ACCEPT );
            errorFilter.setOnMismatch( FilterReply.DENY );

            // Set up the trace and error appenders
            RollingFileAppender rollingFileAppenderTrace = new RollingFileAppender();
            rollingFileAppenderTrace.setContext(loggerContext);
            rollingFileAppenderTrace.setName(name + "-Trace");
            rollingFileAppenderTrace.setFile(name + "-Trace.logger");
            rollingFileAppenderTrace.setEncoder(encoder);
            rollingFileAppenderTrace.setRollingPolicy( tracePolicy );
            rollingFileAppenderTrace.addFilter( traceFilter );
            tracePolicy.setParent( rollingFileAppenderTrace );

            RollingFileAppender rollingFileAppenderError = new RollingFileAppender();
            rollingFileAppenderError.setContext(loggerContext);
            rollingFileAppenderError.setName(name + "-Error");
            rollingFileAppenderError.setFile(name + "-Error.logger");
            rollingFileAppenderError.setEncoder(encoder);
            rollingFileAppenderError.setRollingPolicy( errorPolicy );
            rollingFileAppenderError.addFilter( errorFilter );
            errorPolicy.setParent( rollingFileAppenderError );

            // Start everything
            tracePolicy.start();
            errorPolicy.start();
            traceFilter.start();
            errorFilter.start();
            rollingFileAppenderTrace.start();
            rollingFileAppenderError.start();


            // attach the rolling file appenders to the logger
            Logger logger = (ch.qos.logback.classic.Logger) loggerContext.getLogger(name);
            logger.addAppender(rollingFileAppenderTrace);
            logger.addAppender(rollingFileAppenderError);

        }

        StatusPrinter.print(loggerContext);

        // Test it to see what happens
        for(int i = 0; i < nameList.length; i++) {

            String name = nameList[i];

            Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(name);
            logger.error("error message" );
            logger.warn("warn message" );
            logger.info("info message" );
            logger.debug("debug message" );
            logger.trace("trace message" );
        }

        Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.myapp");
        logger.error( "generic error message ");


    }

}

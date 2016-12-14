package logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Context;
import engine.EventoMotor;
import engine.TipoEvento;
import org.slf4j.LoggerFactory;
import status.Transacao;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by rodrigosol on 11/30/16.
 */
public class InfluuntLogger {

    public static Logger logger = (Logger) LoggerFactory.getLogger("Influunt");

    public static Logger loggerOficial = (Logger) LoggerFactory.getLogger("OficialInfluunt");

    public static TreeSet<TipoEvento> eventosLogaveis = new TreeSet<>();

    public static InfluuntLogAppender appender;

    private static boolean compact = false;

    public static void log(TipoEvento tipoEvento, EventoMotor eventoMotor) {
        if (eventosLogaveis.contains(tipoEvento)) {
            //Log o evento
            loggerOficial.info(tipoEvento.toString());
        }
    }

    private static void setEventos(List<?> eventos) {
        eventos.stream().forEach(e -> eventosLogaveis.add(TipoEvento.valueOf(e.toString())));
    }

    public static void configureLog(String path, String name, int tamanho, boolean compactFormat, List<?> eventos) {
        setEventos(eventos);
        compact = compactFormat;
        appender = new InfluuntLogAppender((Context) LoggerFactory.getILoggerFactory(), path, name, tamanho);
        loggerOficial.addAppender(appender);
        loggerOficial.setLevel(Level.INFO);
    }

    public static void log(TipoLog tipo, String msg) {
        loggerOficial.info(String.format("[%s] %s", tipo, msg));
    }

    public static void log(TipoLog tipo, TipoEvento tipoEvento) {
        if (compact) {
            loggerOficial.info(String.format("[%s] %s", tipo, tipoEvento.toString()));
        } else {
            loggerOficial.info(String.format("[%s] %s", tipo, tipoEvento.toString() + " - " + tipoEvento.getDescricao()));
        }
    }

    public static void log(TipoLog tipoLog, Transacao transacao) {
        log(tipoLog, String.format("[%s] %s", transacao.getTipoTransacao(), transacao.getEtapaTransacao().getMessage()));
    }

    public static void log(TipoLog tipoLog, EventoMotor eventoMotor) {
        if (compact) {
            loggerOficial.info(String.format("[%s] %s", tipoLog, eventoMotor.getTipoEvento().toString()));
        } else {
            loggerOficial.info(String.format("[%s] %s", tipoLog, eventoMotor.getTipoEvento().toString() + " - " + eventoMotor.getTipoEvento().getMessage(eventoMotor.getStringParams())));
        }
    }
}

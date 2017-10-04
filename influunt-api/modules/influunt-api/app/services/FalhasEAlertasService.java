package services;

import engine.TipoEvento;
import engine.TipoEventoControlador;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 11/1/16.
 */
public class FalhasEAlertasService {
    public static List<TipoEvento> getFalhas() {
        return Arrays.stream(TipoEvento.values())
            .filter(tipoEvento -> tipoEvento.getTipoEventoControlador().equals(TipoEventoControlador.FALHA))
            .collect(Collectors.toList());

    }

    public static List<TipoEvento> getAlarmes() {
        return Arrays.stream(TipoEvento.values())
            .filter(tipoEvento -> tipoEvento.getTipoEventoControlador().equals(TipoEventoControlador.ALARME))
            .collect(Collectors.toList());

    }

    public static List<TipoEvento> getRemocaoDeFalhas() {
        return Arrays.stream(TipoEvento.values())
            .filter(tipoEvento -> tipoEvento.getTipoEventoControlador().equals(TipoEventoControlador.REMOCAO_FALHA))
            .collect(Collectors.toList());
    }

    public static List<TipoEvento> getEventosTrocaDePlano() {
        return Arrays.stream(TipoEvento.values())
            .filter(tipoEvento -> tipoEvento.getTipoEventoControlador().equals(TipoEventoControlador.TROCA_PLANO))
            .collect(Collectors.toList());
    }

}

package engine;

import static engine.TipoEvento.IMPOSICAO_PLANO;
import static engine.TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE;
import static engine.TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR;

public enum TipoEventoControlador {
    FALHA,
    ALARME,
    DETECTOR_VEICULAR,
    DETECTOR_PEDESTRE,
    IMPOSICAO_PLANO,
    TROCA_PLANO,
    MODO_MANUAL,
    REMOCAO_FALHA;
}

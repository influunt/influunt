package models;

/**
 * Created by lesiopinheiro on 9/27/16.
 */
public enum MotivoFalhaControlador {

    FASE_VERMELHA_APAGADA,
    FOCO_VERMELHO_APAGADO,
    FALTA_ACIONAMENTO_DETECTOR_VEICULAR,
    ACIONAMENTO_DIRETO_DETECTOR_VEICULAR,
    FALTA_ACIONAMENTO_DETECTOR_PEDESTRE,
    ACIONAMENTO_DIRETO_DETECTOR_PEDESTRE,
    DESRESPEITO_TEMPO_MAXIMO_PERMANENCIA_ESTAGIO,
    VERDES_CONFLITANTES,
    AMARELO_INTERMITENTE_POR_FALHA,
    SEMAFORO_APAGADO,
    FALHA_ACERTO_RELOGIO_GPS,
    QUEDA_ENERGIA,
    FASE_PEDESTRE_QUEIMADA
}
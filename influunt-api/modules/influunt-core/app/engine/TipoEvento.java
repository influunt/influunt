package engine;


import models.TipoDetector;

import java.util.Arrays;
import java.util.Formatter;


public enum TipoEvento {

    //Alarmes
    ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 1, "Abertura da porta principal do controlador", null),
    FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 2, "Fechamento da porta principal do controlador", null),
    ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 3, "Abertura da porta do painel de facilidades do controlador", null),
    FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 4, "Fechamento da porta do painel de facilidades do controlador", null),
    INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL(TipoEventoControlador.ALARME, 5, "Inserção de plug", null),
    RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL(TipoEventoControlador.ALARME, 6, "Retirada de plug", null),

    //Falhas
    FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA(TipoEventoControlador.FALHA, 1, "Fase vermelha do grupo semafórico apagada", "%s: Fase vermelha do grupo semafórico apagada"),

    //Detector
    ACIONAMENTO_DETECTOR_VEICULAR(TipoEventoControlador.DETECTOR_VEICULAR, 1, "Acionamento de detector veicular", "%s foi acionado"),
    ACIONAMENTO_DETECTOR_PEDESTRE(TipoEventoControlador.DETECTOR_PEDESTRE, 2, "Acionamento de detector pedestre", "%s foi acionado"),

    //Modo Manual
    TROCA_ESTAGIO_MANUAL(TipoEventoControlador.MODO_MANUAL, 2, "Troca de estágio no modo manual", "Troca estágio"),

    //Imposicao de Planos
    IMPOSICAO_PLANO(TipoEventoControlador.IMPOSICAO_PLANO, 1, "Imposição de Plano", "Plano %s foi imposto"),

    //Troca de PLanos
    TROCA_DE_PLANO_NO_ANEL(TipoEventoControlador.TROCA_PLANO,1,"Troca de Plano no Anel", "Plano %s está ativo");

    private static Formatter formatter = new Formatter();

    private final int codigo;

    private final String descricao;

    private final String template;

    private final TipoEventoControlador tipoEventoControlador;

    TipoEvento(TipoEventoControlador tipoEventoControlador, int codigo, String descricao, String template) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.template = template;
        this.tipoEventoControlador = tipoEventoControlador;
    }

    public static TipoEvento getFalha(final int falha) {
        return Arrays.stream(TipoEvento.values()).filter(tipoEvento -> tipoEvento.match(TipoEventoControlador.FALHA, falha)).findFirst().orElse(null);
    }

    public static TipoEvento getAlarme(final int falha) {
        return Arrays.stream(TipoEvento.values()).filter(tipoEvento -> tipoEvento.match(TipoEventoControlador.ALARME, falha)).findFirst().orElse(null);
    }

    public static TipoEvento getDetector(TipoDetector tipoDetector) {
        if (tipoDetector.equals(TipoDetector.VEICULAR)) {
            return ACIONAMENTO_DETECTOR_VEICULAR;
        } else {
            return ACIONAMENTO_DETECTOR_PEDESTRE;
        }
    }

    public String getMessage(String... args) {
        if (template != null) {
            return formatter.format(template, args).toString();
        } else {
            return descricao;
        }
    }

    public int getCodigo() {
        return this.getCodigo();
    }

    public TipoEventoControlador getTipoEventoControlador() {
        return this.tipoEventoControlador;
    }

    public boolean match(TipoEventoControlador tipoEventoControlador, int codigo) {
        return this.tipoEventoControlador.equals(tipoEventoControlador) && this.codigo == codigo;
    }
}

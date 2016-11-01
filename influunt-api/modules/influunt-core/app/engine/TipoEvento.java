package engine;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.serializers.AreaSerializer;
import json.serializers.TipoEventoSerializer;
import models.TipoDetector;

import java.util.Arrays;
import java.util.Formatter;

@JsonSerialize(using = TipoEventoSerializer.class)
public enum TipoEvento {

    //Alarmes
    ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 1, "Abertura da porta principal do controlador", null,null),
    ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 2, "Fechamento da porta principal do controlador", null,null),
    ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 3, "Abertura da porta do painel de facilidades do controlador", null,null),
    ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 4, "Fechamento da porta do painel de facilidades do controlador", null,null),
    ALARME_INSERCAO_DE_PLUG(TipoEventoControlador.ALARME, 5, "Inserção de plug", null,null),
    ALARME_RETIRADA_DO_PLUG(TipoEventoControlador.ALARME, 6, "Retirada de plug", null,null),

    //Falhas
    FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA(TipoEventoControlador.FALHA, 1, "Fase vermelha do grupo semafórico apagada", "%s: Fase vermelha do grupo semafórico apagada", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO(TipoEventoControlador.FALHA, 2, "Fase vermelha do grupo semafórico apagada removida", "%s: Fase vermelha do grupo semafórico apagada removida", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA(TipoEventoControlador.FALHA, 7, "Foco vermelho apagado", "%s: Foco vermelho apagado", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO(TipoEventoControlador.FALHA, 8, "Foco vermelho apagado removida", "%s: Foco vermelho apagado removida", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO(TipoEventoControlador.FALHA, 13, "Falha detector veicular - Falta de Acionamento", "%s: Falha detector veicular - Falta de Acionamento", new TipoEventoParamsDescriptor("Detector Veícular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),
    FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO(TipoEventoControlador.FALHA, 14, "Falha detector veicular - Acionamento Direto", "%s: Falha detector veicular - Acionamento Direto", new TipoEventoParamsDescriptor("Detector Veícular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),
    FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO(TipoEventoControlador.FALHA, 15, "Falha detector pedestre - Falta de Acionamento", "%s: Falha pedestre veicular - Falta de Acionamento", new TipoEventoParamsDescriptor("Detector Pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),
    FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO(TipoEventoControlador.FALHA, 16, "Falha detector pedestre - Acionamento Direto", "%s: Falha pedestre veicular - Acionamento Direto", new TipoEventoParamsDescriptor("Detector Pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),
    FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO(TipoEventoControlador.FALHA, 26, "Desrespeito ao tempo máximo de permanencia no estágio", "%s: Desrespeito ao tempo máximo de permanencia no estágio", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),
    FALHA_VERDES_CONFLITANTES(TipoEventoControlador.FALHA, 27, "Verdes conflitantes", "Verdes conflitantes", null),
    FALHA_AMARELO_INTERMITENTE(TipoEventoControlador.FALHA, 28, "Amarelo Intermitente", "Amarelo Intermitente", null),
    FALHA_SEMAFORO_APAGADO(TipoEventoControlador.FALHA, 29, "Semáforo apagado", "Semafóro apagado", null),
    FALHA_ACERTO_RELOGIO_GPS(TipoEventoControlador.FALHA, 52, "Falha acerto relógio GPS", "Falha acerto relógio GPS", null),

    //Detector
    ACIONAMENTO_DETECTOR_VEICULAR(TipoEventoControlador.DETECTOR_VEICULAR, 1, "Acionamento de detector veicular", "%s foi acionado",null),
    ACIONAMENTO_DETECTOR_PEDESTRE(TipoEventoControlador.DETECTOR_PEDESTRE, 2, "Acionamento de detector pedestre", "%s foi acionado",null),

    //Imposicao de Planos
    IMPOSICAO_PLANO(TipoEventoControlador.IMPOSICAO_PLANO, 1, "Imposição de Plano", "Plano %s foi imposto",null),

    //Troca de PLanos
    TROCA_DE_PLANO_NO_ANEL(TipoEventoControlador.TROCA_PLANO,1,"Troca de Plano no Anel", "Plano %s está ativo",null);

    private static Formatter formatter = new Formatter();

    private final int codigo;

    private final String descricao;

    private final String template;

    private final TipoEventoControlador tipoEventoControlador;

    private final TipoEventoParamsDescriptor paramsDescriptor;

    TipoEvento(TipoEventoControlador tipoEventoControlador, int codigo, String descricao, String template, TipoEventoParamsDescriptor paramsDescriptor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.template = template;
        this.tipoEventoControlador = tipoEventoControlador;
        this.paramsDescriptor = paramsDescriptor;
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
        return this.codigo;
    }

    public TipoEventoParamsDescriptor getParamsDescriptor() {
        return paramsDescriptor;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoEventoControlador getTipoEventoControlador() {
        return this.tipoEventoControlador;
    }

    public boolean match(TipoEventoControlador tipoEventoControlador, int codigo) {
        return this.tipoEventoControlador.equals(tipoEventoControlador) && this.codigo == codigo;
    }
}

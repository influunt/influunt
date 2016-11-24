package engine;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.serializers.TipoEventoSerializer;
import models.TipoDetector;

import java.util.Arrays;
import java.util.Formatter;

@JsonSerialize(using = TipoEventoSerializer.class)
public enum TipoEvento {

    //Alarmes
    //Não alteram a programacao em execucao no controlador
    ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 1, "Abertura da porta principal do controlador", null, null),
    ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR(TipoEventoControlador.ALARME, 2, "Fechamento da porta principal do controlador", null, null),
    ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 3, "Abertura da porta do painel de facilidades do controlador", null, null),
    ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR(TipoEventoControlador.ALARME, 4, "Fechamento da porta do painel de facilidades do controlador", null, null),
    ALARME_AMARELO_INTERMITENTE(TipoEventoControlador.ALARME, 29, "Amarelo Intermitente", "Amarelo Intermitente", null),
    ALARME_SEMAFORO_APAGADO(TipoEventoControlador.ALARME, 30, "Semáforo apagado", "Semafóro apagado", null),
    ALARME_ACERTO_RELOGIO_GPS(TipoEventoControlador.ALARME, 52, "Falha acerto relógio GPS", "Falha acerto relógio GPS", null),
    ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA(TipoEventoControlador.ALARME, 7, "Foco vermelho apagado", "Anel %s: Foco vermelho do G%s apagado", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO(TipoEventoControlador.ALARME, 8, "Foco vermelho apagado removida", "Anel %s: Foco vermelho do G%s apagado removida", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),


    //Falhas
    //Alteram a programacao vigente do controlador
    FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO(TipoEventoControlador.FALHA, 1, "Detector pedestre - Falta de acionamento", "Anel %s: Falha no DP%s - Falta de Acionamento", new TipoEventoParamsDescriptor("Detector Pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),
    FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO(TipoEventoControlador.FALHA, 2, "Detector pedestre - Acionamento direto", "Anel %s: Falha no DP%s - Acionamento Direto", new TipoEventoParamsDescriptor("Detector Pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),

    FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO(TipoEventoControlador.FALHA, 3, "Detector veicular - Falta de acionamento", "Anel %s: Falha no DV%s - Falta de Acionamento", new TipoEventoParamsDescriptor("Detector Veicular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),
    FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO(TipoEventoControlador.FALHA, 4, "Detector veicular - Acionamento direto", "Anel %s: Falha no DV%s - Acionamento Direto", new TipoEventoParamsDescriptor("Detector Veicular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),

    FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO(TipoEventoControlador.FALHA, 5, true, "Desrespeito ao tempo máximo de permanência no estágio", "Anel %s: Desrespeito ao tempo máximo de permanência no estágio", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),

    FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA(TipoEventoControlador.FALHA, 6, true, "Fase vermelha do grupo semafórico apagada", "Anel %s: Fase vermelha do G%s apagada", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),

    FALHA_SEQUENCIA_DE_CORES(TipoEventoControlador.FALHA, 7, true, "Falha sequencia de cores", "Anel %s: Falha sequencia de cores", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),

    FALHA_VERDES_CONFLITANTES(TipoEventoControlador.FALHA, 8, true, "Verdes conflitantes", "Anel %s: Verdes conflitantes", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),

    FALHA_WATCH_DOG(TipoEventoControlador.FALHA, 9, true, "Falha CPU", "Falha CPU", null),
    FALHA_MEMORIA(TipoEventoControlador.FALHA, 10, true, "Falha Memoria", "Falha Memoria", null),

    //Remocao de Falhas
    REMOCAO_FALHA_DETECTOR_PEDESTRE(TipoEventoControlador.REMOCAO_FALHA, 1, "Detector pedestre - Remoção de falha", "Anel %s: Falha no DP%s removida", new TipoEventoParamsDescriptor("Detector pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),
    REMOCAO_FALHA_DETECTOR_VEICULAR(TipoEventoControlador.REMOCAO_FALHA, 2, "Detector veicular - Remoção de falha", "Anel %s: Falha no DV%s removida", new TipoEventoParamsDescriptor("Detector veicular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),
    REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO(TipoEventoControlador.REMOCAO_FALHA, 3, true, "Fase vermelha do grupo semafórico apagada removida", "%s: Fase vermelha do G%s apagada removida", new TipoEventoParamsDescriptor("Grupo Semafórico", TipoEventoParamsTipoDeDado.GRUPO_SEMAFORICO)),
    REMOCAO_FALHA_VERDES_CONFLITANTES(TipoEventoControlador.REMOCAO_FALHA, 4, true, "Verdes conflitantes removido", "Anel %s: Verdes conflitantes removido", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),


    //Detector
    ACIONAMENTO_DETECTOR_VEICULAR(TipoEventoControlador.DETECTOR_VEICULAR, 1, "Acionamento de detector veicular", "%s foi acionado", new TipoEventoParamsDescriptor("Detector veicular", TipoEventoParamsTipoDeDado.DETECTOR_VEICULAR)),
    ACIONAMENTO_DETECTOR_PEDESTRE(TipoEventoControlador.DETECTOR_PEDESTRE, 1, "Acionamento de detector pedestre", "%s foi acionado", new TipoEventoParamsDescriptor("Detector pedestre", TipoEventoParamsTipoDeDado.DETECTOR_PEDESTRE)),

    //Modo Manual
    INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL(TipoEventoControlador.MODO_MANUAL, 1, "Inserção de plug", null, null),
    RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL(TipoEventoControlador.MODO_MANUAL, 2, "Retirada de plug", null, null),
    TROCA_ESTAGIO_MANUAL(TipoEventoControlador.MODO_MANUAL, 3, "Troca de estágio no modo manual", "Troca estágio", null),
    MODO_MANUAL_ATIVADO(TipoEventoControlador.MODO_MANUAL,4,"Modo manual ativado", "Modo manual ativado",null),

    //Imposicoes
    IMPOSICAO_PLANO(TipoEventoControlador.IMPOSICAO, 1, "Imposição de Plano", "Anel %s: Plano %s foi imposto com duração de %s minutos", new TipoEventoParamsDescriptor("Plano", TipoEventoParamsTipoDeDado.PLANO)),
    IMPOSICAO_MODO(TipoEventoControlador.IMPOSICAO, 2, "Imposição de Modo", "Anel %s: Modo de operação %s foi imposto", new TipoEventoParamsDescriptor("Plano", TipoEventoParamsTipoDeDado.MODO_OPERACAO_PLANO)),
    LIBERAR_IMPOSICAO(TipoEventoControlador.IMPOSICAO, 3, "Liberação de imposição", "Anel %s: Foi liberado da imposição", new TipoEventoParamsDescriptor("Anel", TipoEventoParamsTipoDeDado.ANEL)),

    //Troca de PLanos
    TROCA_DE_PLANO_NO_ANEL(TipoEventoControlador.TROCA_PLANO, 1, "Troca de Plano no Anel", "Plano %s está ativo", null);

    private final int codigo;

    private final String descricao;

    private final String template;

    private final TipoEventoControlador tipoEventoControlador;

    private final TipoEventoParamsDescriptor paramsDescriptor;

    private boolean entraEmIntermitente = false;

    TipoEvento(TipoEventoControlador tipoEventoControlador, int codigo, String descricao, String template, TipoEventoParamsDescriptor paramsDescriptor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.template = template;
        this.tipoEventoControlador = tipoEventoControlador;
        this.paramsDescriptor = paramsDescriptor;
    }

    TipoEvento(TipoEventoControlador tipoEventoControlador, int codigo, boolean entraEmIntermitente, String descricao, String template, TipoEventoParamsDescriptor paramsDescriptor) {
        this.entraEmIntermitente = entraEmIntermitente;
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

    public static TipoEvento getByTipoECodigo(TipoEventoControlador tipoEventoControlador, Integer codigo) {
        return Arrays.stream(TipoEvento.values()).filter(e -> e.getTipoEventoControlador().equals(tipoEventoControlador))
            .filter(e -> e.getCodigo() == codigo)
            .findFirst().orElse(null);
    }

    public String getMessage(String... args) {
        if (template != null) {
            return new Formatter().format(template, args).toString();
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

    public boolean isEntraEmIntermitente() {
        return entraEmIntermitente;
    }
}

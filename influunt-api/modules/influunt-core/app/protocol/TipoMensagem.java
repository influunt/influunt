package protocol;

/**
 * Created by rodrigosol on 9/6/16.
 */
public enum TipoMensagem {
    ERRO,

    //Mensagens enviadas para device (Central -> Device)
    CONFIGURACAO,
    TRANSACAO,
    LER_DADOS_CONTROLADOR,

    //Mensagens enviadas para central (Device -> Central)
    MUDANCA_STATUS_CONTROLADOR,
    CONTROLADOR_ONLINE,
    CONTROLADOR_OFFLINE,
    CONFIGURACAO_INICIAL,
    CONFIGURACAO_OK,
    ALARME_FALHA,
    TROCA_DE_PLANO,
    REMOCAO_FALHA,
    INFO,

    //Mensagens internas (Central -> APP)
    PACOTE_TRANSACAO,
    STATUS_TRANSACAO,

    //Mensagens internas (Device -> Motor)
    IMPOSICAO_DE_PLANO,
    IMPOSICAO_DE_PLANO_TEMPORARIO,
    IMPOSICAO_DE_MODO_OPERACAO,
    LIBERAR_IMPOSICAO,
    TROCAR_TABELA_HORARIA,
    TROCAR_TABELA_HORARIA_IMEDIATAMENTE,
    TROCAR_PLANOS,
    ATUALIZAR_CONFIGURACAO;
}

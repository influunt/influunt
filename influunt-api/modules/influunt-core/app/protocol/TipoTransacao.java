package protocol;

/**
 * Created by rodrigosol on 9/6/16.
 */
public enum TipoTransacao {
    // Envio de pacote de planos
    PACOTE_PLANO,

    // Envio de pacote de tabela horária
    PACOTE_TABELA_HORARIA,

    // Envio de configuração + pacote de planos e tabela horária
    CONFIGURACAO_COMPLETA,

    // Imposição de modo de operação
    IMPOSICAO_MODO_OPERACAO,

    // Imposição de plano
    IMPOSICAO_PLANO,

    // Imposição de plano temporário
    IMPOSICAO_PLANO_TEMPORARIO,

    // Liherar anel da imposição de plano ou modo operação
    LIBERAR_IMPOSICAO,

    // Coloca o controlador no statsu Manutenção
    COLOCAR_CONTROLADOR_MANUTENCAO,

    // Inativa o controlador
    INATIVAR_CONTROLADOR
}

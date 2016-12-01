package protocol;

/**
 * Created by rodrigosol on 9/6/16.
 */
public enum TipoTransacao {
    // Envio de pacote de planos e tabela horária
    PACOTE_PLANO,

    // Envio de configuração + pacote de planos e tabela horária
    CONFIGURACAO_COMPLETA,

    // Imposição de modo de operação
    IMPOSICAO_MODO_OPERACAO,

    // Imposição de plano
    IMPOSICAO_PLANO,

    // Liherar anel da imposição de plano ou modo operação
    LIBERAR_IMPOSICAO
}

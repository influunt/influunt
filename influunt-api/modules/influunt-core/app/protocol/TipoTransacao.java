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
    IMPOSICAO_MODO
}

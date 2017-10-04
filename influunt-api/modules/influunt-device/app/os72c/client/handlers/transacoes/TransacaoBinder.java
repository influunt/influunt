package os72c.client.handlers.transacoes;

import protocol.TipoTransacao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 12/8/16.
 */
public class TransacaoBinder {
    private static Map<TipoTransacao, Class> bindings = new HashMap<>();

    static {
        bindings.put(TipoTransacao.CONFIGURACAO_COMPLETA, TransacaoConfiguracaoCompletaActorHandler.class);
        bindings.put(TipoTransacao.IMPOSICAO_MODO_OPERACAO, TransacaoImposicaoModoOperacaoActorHandler.class);
        bindings.put(TipoTransacao.IMPOSICAO_PLANO, TransacaoImposicaoPlanoActorHandler.class);
        bindings.put(TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO, TransacaoImposicaoPlanoTemporarioActorHandler.class);
        bindings.put(TipoTransacao.LIBERAR_IMPOSICAO, TransacaoLiberarImposicaoActorHandler.class);
        bindings.put(TipoTransacao.PACOTE_PLANO, TransacaoPacotePlanoActorHandler.class);
        bindings.put(TipoTransacao.PACOTE_TABELA_HORARIA, TransacaoPacoteTabelaHorariaActorHandler.class);
        bindings.put(TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO, TransacaoColocarControladorManutencaoActorHandler.class);
        bindings.put(TipoTransacao.INATIVAR_CONTROLADOR, TransacaoInativarControladorActorHandler.class);
        bindings.put(TipoTransacao.ATIVAR_CONTROLADOR, TransacaoAtivarControladorActorHandler.class);
    }

    public static Class getClass(TipoTransacao tipoTransacao) {
        return bindings.get(tipoTransacao);
    }
}

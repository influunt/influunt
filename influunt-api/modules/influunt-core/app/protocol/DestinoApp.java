package protocol;

/**
 * Created by leonardo on 9/14/16.
 */
public class DestinoApp {

    public final static String transacao(String transacaoId) {
        return "app/transacoes/".concat(transacaoId);
    }
}

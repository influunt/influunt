package protocol;

/**
 * Created by leonardo on 9/14/16.
 */
public class DestinoApp {

    public final static String transacao(String transacaoId) {
        return "app/transacoes/".concat(transacaoId);
    }

    public final static String statusTransacao(String transacaoId) {
        return "app/transacoes/".concat(transacaoId).concat("/status");
    }

    public final static String controladorOnline() {
        return "app/conn/online";
    }

    public final static String controladorOffline() {
        return "app/conn/offline";
    }

    public final static String trocaPlano() {
        return "app/troca_plano";
    }

    public final static String alarmesEFalhas() {
        return "app/alarmes_falhas";
    }

    public final static String mudancaStatusControlador() {
        return "app/mudanca_status_controlador";
    }

    public final static String dadosControlador(String envelopeId) {
        return "app/controlador/".concat(envelopeId).concat("/dados");
    }
}

package protocol;

/**
 * Created by leonardo on 9/14/16.
 */
public class DestinoCentral {

    public final static String envioDeStatus() {
        return "central/mudanca_status_controlador";
    }

    public final static String pedidoConfiguracao() {
        return "central/configuracao";
    }

    public final static String alarmeFalhaConfiguracao(String idControlador) {
        return "central/alarmes_falhas/" + idControlador;
    }

    public static String transacao(String idTransacao) {
        return "central/transacoes/".concat(idTransacao);
    }
}

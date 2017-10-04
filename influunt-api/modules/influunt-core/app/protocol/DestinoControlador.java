package protocol;

/**
 * Created by leonardo on 9/14/16.
 */
public class DestinoControlador {

    public final static String transacao(String idControlador) {
        return "controlador/".concat(idControlador).concat("/transacoes");
    }

    public final static String leituraDadosControlador(String idControlador) {
        return "controlador/".concat(idControlador).concat("/info");
    }

    public final static String online() {
        return "controladores/conn/online";
    }

    public final static String offline() {
        return "controladores/conn/offline";
    }
}

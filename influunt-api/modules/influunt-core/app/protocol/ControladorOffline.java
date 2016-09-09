package protocol;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ControladorOffline {
    public long dataHora;


    private ControladorOffline() {
    }

    public static Envelope getMensagem(String idControlador) {
        return new Envelope(TipoMensagem.CONTROLADOR_OFFLINE, idControlador, "controladores/conn/offline", 1, null, null);
    }

}

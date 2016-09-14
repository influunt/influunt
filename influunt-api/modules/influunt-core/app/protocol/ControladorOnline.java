package protocol;

import models.StatusDevice;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ControladorOnline {
    public long dataHora;

    public String versao72c;

    public StatusDevice status;

    private ControladorOnline(long dataHora, String versao72c, StatusDevice status) {
        this.dataHora = dataHora;
        this.versao72c = versao72c;
        this.status = status;
    }

    public static Envelope getMensagem(String idControlador, Long dataHora, String versao, StatusDevice status) {
        ControladorOnline controladorOnline = new ControladorOnline(dataHora, versao, status);
        return new Envelope(TipoMensagem.CONTROLADOR_ONLINE, idControlador, "controladores/conn/online", 1, controladorOnline, null);
    }

}

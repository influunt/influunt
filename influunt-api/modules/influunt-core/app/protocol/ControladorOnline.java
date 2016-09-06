package protocol;

import models.StatusControlador;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ControladorOnline {
        public long dataHora;
        public String versao72c;

        public StatusControlador status;

        private ControladorOnline(long dataHora,String versao72c, StatusControlador status){
            this.dataHora = dataHora;
            this.versao72c = versao72c;
            this.status = status;
        }

        public static Envelope getMensagem(String idControlador, Long dataHora,String versao, StatusControlador statusControlador){
            ControladorOnline controladorOnline = new ControladorOnline(dataHora,versao,statusControlador);
            return new Envelope(TipoMensagem.CONTROLADOR_ONLINE,idControlador,"controladores/conn/online",1,controladorOnline,null);
        }

}

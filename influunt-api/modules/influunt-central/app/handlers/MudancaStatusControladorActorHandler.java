package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.gson.internal.LinkedTreeMap;
import models.StatusDevice;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.StatusControladorFisico;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class MudancaStatusControladorActorHandler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.MUDANCA_STATUS_CONTROLADOR)) {
                log.info("O controlador: {} esta mundando de erros", envelope.getIdControlador());
                LinkedTreeMap map = (LinkedTreeMap) envelope.getConteudo();
                StatusControladorFisico.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), StatusDevice.valueOf(map.get("status").toString()));
            }
        }
    }
}

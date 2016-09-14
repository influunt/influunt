package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.StatusDevice;
import protocol.Envelope;
import protocol.MudancaStatusControlador;
import protocol.TipoMensagem;
import status.StatusConexaoControlador;
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
                log.info("O controlador: {} esta mundando de status", envelope.getIdControlador());
                MudancaStatusControlador mudancaStatusControlador = (MudancaStatusControlador) envelope.getConteudo();
                StatusControladorFisico.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), mudancaStatusControlador.status);
            }
        }
    }
}

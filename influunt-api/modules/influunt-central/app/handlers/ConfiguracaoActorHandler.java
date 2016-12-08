package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Controlador;
import models.ControladorFisico;
import models.StatusDevice;
import models.VersaoControlador;
import protocol.Configuracao;
import protocol.Envelope;
import protocol.TipoMensagem;
import utils.AtoresCentral;

import java.util.UUID;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONFIGURACAO_INICIAL) && envelope.getEmResposta() == null) {
                Envelope envelope1 = Configuracao.getMensagem(envelope);
                log.info("[CENTRAL] - ENVIANDO CONFIGURACAO: " + envelope1.getTipoMensagem());
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope1, getSelf());
            } else if(envelope.getTipoMensagem().equals(TipoMensagem.CONFIGURACAO_OK) && envelope.getEmResposta() != null) {
                ControladorFisico controladorFisico = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()));
                Controlador controladorConfigurado = controladorFisico.getControladorConfiguradoOuSincronizado();
                controladorConfigurado.setSincronizado(true);
                VersaoControlador versaoControlador = controladorConfigurado.getVersaoControlador();
                versaoControlador.ativar();
                controladorFisico.setControladorSincronizado(controladorConfigurado);
                controladorFisico.setStatusDevice(StatusDevice.ATIVO);

                versaoControlador.update();
                controladorConfigurado.update();
                controladorFisico.update();

                log.info("[CENTRAL] - Controlador confirmando o recebimento da configuração");
            }
        }
    }
}

package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Controlador;
import models.ControladorFisico;
import models.VersaoControlador;
import protocol.Configuracao;
import protocol.Envelope;
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
            if (envelope.getEmResposta() == null) {
                Envelope envelope1 = Configuracao.getMensagem(envelope);
                log.info("[CENTRAL] - ENVIANDO CONFIGURACAO: " + envelope1.getTipoMensagem());
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope1, getSelf());
            } else {
                ControladorFisico controladorFisico = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()));
                Controlador controladorConfigurado = controladorFisico.getControladorConfigurado();
                controladorConfigurado.setSincronizado(true);
                controladorConfigurado.getVersaoControlador().ativar();
                controladorFisico.setControladorSincronizado(controladorConfigurado);
                controladorFisico.update();

                log.info("[CENTRAL] - Controlador confirmando o recebimento da configuração: {}", envelope.getConteudo().toString());
            }
        }
    }

    public void postStop() throws Exception {
        System.out.println(" ConfiguracaoActorHandler morreu" );
        super.postStop();
    }

}

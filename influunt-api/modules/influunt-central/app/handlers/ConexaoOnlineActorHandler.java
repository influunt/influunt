package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import models.ControladorFisico;
import play.libs.Json;
import protocol.DestinoApp;
import protocol.Envelope;
import protocol.TipoMensagem;
import status.StatusConexaoControlador;
import utils.AtoresCentral;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConexaoOnlineActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);



    @Override
    public void onReceive(Object message) {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONTROLADOR_ONLINE)) {
                StatusConexaoControlador status = StatusConexaoControlador.ultimoStatus(envelope.getIdControlador());
                if(status!= null && status.isConectado()){
                    return;
                }

                log.info("O controlador: {} esta online", envelope.getIdControlador());

                envelope.setDestino(DestinoApp.controladorOnline());
                envelope.setCriptografado(false);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());

                StatusConexaoControlador.log(envelope.getIdControlador(), envelope.getCarimboDeTempo(), true);

                ControladorFisico controladorFisico = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()));

                final JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());
                controladorFisico.setMarca(jsonConteudo.get("marca").asText());
                controladorFisico.setModelo(jsonConteudo.get("modelo").asText());

                Controlador controlador = controladorFisico.getControladorSincronizadoOuConfigurado();
                if (controlador != null) {
                    controlador.setFirmware(jsonConteudo.get("firmware").asText());
                    controlador.update();
                }

                controladorFisico.update();
            }
        }
    }
}

package handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.ControladorFisico;
import org.joda.time.DateTime;
import play.libs.Json;
import protocol.Envelope;
import protocol.TipoMensagem;

import java.util.UUID;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class InfoActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.INFO)) {
                log.info("O informacoes sobre o hardware do controlador : {}", envelope.getIdControlador());
                final JsonNode jsonConteudo = Json.parse(envelope.getConteudo().toString());

                ControladorFisico controladorFisico = ControladorFisico.find.byId(UUID.fromString(envelope.getIdControlador()));
                controladorFisico.setFabricanteHardware(jsonConteudo.get("fabricante").asText());
                controladorFisico.setModeloHardware(jsonConteudo.get("modelo").asText());
                controladorFisico.setVersaoFirmwareHardware(jsonConteudo.get("versao").asText());
                controladorFisico.setAtualizacaoVersaoHardware(DateTime.now());

                controladorFisico.update();
            }
        }
    }
}

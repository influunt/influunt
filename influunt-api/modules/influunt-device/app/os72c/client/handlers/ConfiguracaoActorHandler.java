package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Controlador;
import models.StatusDevice;
import os72c.client.storage.Storage;
import os72c.client.utils.Atores;
import protocol.*;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    private final Storage storage;

    private final String idControlador;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public ConfiguracaoActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONFIGURACAO)) {
                if (envelope.getEmResposta() == null) {
                    log.info("Mensagem de configuração errada: {}", envelope.getConteudo().toString());
                } else {
                    log.info("Central respondeu a configuração: {}", envelope.getConteudo().toString());
                    Envelope envelopeSinal;
                    Envelope envelopeStatus;
                    Controlador controlador = Controlador.isValido(envelope.getConteudo());
                    if (controlador != null) {
                        storage.setControlador(controlador);
                        storage.setStatus(StatusDevice.CONFIGURADO);
                        log.info("Responder OK para Central: {}", envelope.getConteudo().toString());
                        envelopeSinal = Sinal.getMensagem(TipoMensagem.OK, idControlador, DestinoCentral.pedidoConfiguracao());
                    } else {
                        log.info("Responder ERRO para Central: {}", envelope.getConteudo().toString());
                        envelopeSinal = Sinal.getMensagem(TipoMensagem.ERRO, idControlador, DestinoCentral.pedidoConfiguracao());
                    }
                    envelopeSinal.setEmResposta(envelope.getIdMensagem());
                    envelopeStatus = MudancaStatusControlador.getMensagem(idControlador, storage.getStatus());
                    getContext().actorSelection(Atores.mqttActorPath(idControlador)).tell(envelopeSinal, getSelf());
                    getContext().actorSelection(Atores.mqttActorPath(idControlador)).tell(envelopeStatus, getSelf());
                }
            }
        } else if (message instanceof String) {
            if (message.toString().equals("VERIFICA")) {
                log.info("Solicita configuração a central: {}", sender());
                getContext().actorSelection(Atores.mqttActorPath(idControlador)).tell(Sinal.getMensagem(TipoMensagem.CONFIGURACAO_INICIAL, idControlador, "central/configuracao"), getSelf());
            }
        }
    }
}

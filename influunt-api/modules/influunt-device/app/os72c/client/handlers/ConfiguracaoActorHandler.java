package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Anel;
import models.Controlador;
import models.StatusAnel;
import models.StatusDevice;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import protocol.*;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    private final Storage storage;

    private final String idControlador;

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public ConfiguracaoActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (TipoMensagem.CONFIGURACAO.equals(envelope.getTipoMensagem())) {
                if (envelope.getEmResposta() == null) {
                } else {
                    Envelope envelopeSinal;
                    Envelope envelopeStatus = null;
                    Controlador controlador = Controlador.isValido(envelope.getConteudo());
                    if (controlador != null) {
                        storage.setControlador(controlador);
                        storage.setStatus(StatusDevice.CONFIGURADO);
                        controlador.getAneisAtivos().stream().filter(Anel::isAceitaModoManual).forEach(anel -> {
                            storage.setStatusAnel(anel.getPosicao(), StatusAnel.NORMAL);
                        });

                        envelopeSinal = Sinal.getMensagem(TipoMensagem.CONFIGURACAO_OK, idControlador, DestinoCentral.pedidoConfiguracao());
                        envelopeStatus = MudancaStatusControlador.getMensagem(idControlador, storage.getStatus(), storage.getStatusAneis());
                    } else {
                        envelopeSinal = Sinal.getMensagem(TipoMensagem.CONFIGURACAO_ERRO, idControlador, DestinoCentral.pedidoConfiguracao());
                    }
                    envelopeSinal.setEmResposta(envelope.getIdMensagem());

                    getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelopeSinal, getSelf());
                    if (envelopeStatus != null) {
                        getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelopeStatus, getSelf());
                    }

                    //Se controlador completo, avisa o motor para colocar o controlador no ar
                    if (storage.getControlador() != null && storage.getControlador().isCompleto()) {
                        getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeSinal, getSelf());
                    }
                }
            }
        } else if (message instanceof String) {
            if (message.toString().equals("VERIFICA")) {
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(Sinal.getMensagem(TipoMensagem.CONFIGURACAO_INICIAL, idControlador, "central/configuracao"), getSelf());
            }
        }
    }
}

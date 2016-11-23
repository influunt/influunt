package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Controlador;
import models.StatusDevice;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import status.Transacao;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoActorHandler extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private String idControlador;

    private Storage storage;

    public TransacaoActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                Controlador controlador = null;
                log.info("DEVICE - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao) {
                    case PREPARE_TO_COMMIT:
                        switch (transacao.tipoTransacao) {
                            case PACOTE_PLANO:
                                controlador = Controlador.isPacotePlanosValido(storage.getControladorJson(), transacao.payload);
                                if (controlador != null) {
                                    storage.setPlanos(Json.parse(transacao.payload.toString()));
                                    storage.setControlador(controlador);
                                    storage.setStatus(StatusDevice.ATIVO);
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case CONFIGURACAO_COMPLETA:
                                JsonNode payloadJson = Json.parse(transacao.payload.toString());
                                JsonNode planoJson = payloadJson.get("pacotePlanos");
                                JsonNode controladorJson = payloadJson.get("pacoteConfiguracao");
                                controlador = Controlador.isPacotePlanosValido(controladorJson, planoJson);
                                if (controlador != null) {
                                    storage.setControlador(controlador);
                                    storage.setPlanos(planoJson);
                                    // Esse status é o correto? Ou deveria ser ATIVO?
                                    storage.setStatus(StatusDevice.CONFIGURADO);
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            default:
                                transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                break;
                        }
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    case COMMIT:
                        transacao.etapaTransacao = EtapaTransacao.COMMITED;
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    case ABORT:
                        transacao.etapaTransacao = EtapaTransacao.ABORTED;
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    default:
                        break;
                }

                log.info("DEVICE - TX Enviada: {}", transacao);
                envelope.setConteudo(transacao.toJson().toString());
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
            }
        }
    }
}

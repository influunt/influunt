package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import models.StatusDevice;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.*;
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
                JsonNode payloadJson = null;
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
                                payloadJson = Json.parse(transacao.payload.toString());
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

                            case IMPOSICAO_MODO_OPERACAO:
                                try {
                                    payloadJson = Json.parse(transacao.payload.toString());
                                    ModoOperacaoPlano.valueOf(payloadJson.get("modoOperacao").asText());
                                    int numeroAnel = payloadJson.get("numeroAnel").asInt();
                                    Long horarioEntrada = payloadJson.get("horarioEntrada").asLong();
                                    int duracao = payloadJson.get("duracao").asInt();
                                    if (numeroAnel < 1 || duracao < 15 || duracao > 600 || horarioEntrada <= System.currentTimeMillis()) {
                                        transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                    } else {
                                        transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                    }
                                } catch (Exception e) {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case IMPOSICAO_PLANO:
                                payloadJson = Json.parse(transacao.payload.toString());
                                int posicaoPlano = payloadJson.get("posicaoPlano").asInt();
                                int numeroAnel = payloadJson.get("numeroAnel").asInt();
                                Long horarioEntrada = payloadJson.get("horarioEntrada").asLong();
                                int duracao = payloadJson.get("duracao").asInt();

                                controlador = storage.getControlador();
                                boolean planoNaoConfigurado = !isPlanoConfigurado(controlador, numeroAnel, posicaoPlano);

                                if (posicaoPlano < 0 || numeroAnel < 1 || duracao < 15 || duracao > 600 || planoNaoConfigurado || horarioEntrada <= System.currentTimeMillis()) {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                }
                                break;

                            case LIBERAR_IMPOSICAO:
                                payloadJson = Json.parse(transacao.payload.toString());
                                if (payloadJson.get("numeroAnel").asInt() < 1) {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                }
                                break;

                            default:
                                transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                break;
                        }
                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                        break;

                    case COMMIT:
                        switch (transacao.tipoTransacao) {
                            case IMPOSICAO_MODO_OPERACAO:
                                payloadJson = Json.parse(transacao.payload.toString());

                                Envelope envelopeImposicaoModo = MensagemImposicaoModoOperacao.getMensagem(
                                    idControlador,
                                    payloadJson.get("modoOperacao").asText(),
                                    payloadJson.get("numeroAnel").asInt(),
                                    payloadJson.get("horarioEntrada").asLong(),
                                    payloadJson.get("duracao").asInt());
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoModo, getSelf());

                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

                            case IMPOSICAO_PLANO:
                                payloadJson = Json.parse(transacao.payload.toString());
                                Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
                                    idControlador,
                                    payloadJson.get("posicaoPlano").asInt(),
                                    payloadJson.get("numeroAnel").asInt(),
                                    payloadJson.get("horarioEntrada").asLong(),
                                    payloadJson.get("duracao").asInt());
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

                            case LIBERAR_IMPOSICAO:
                                payloadJson = Json.parse(transacao.payload.toString());
                                Envelope envelopeLiberarImposicao = MensagemLiberarImposicao.getMensagem(
                                    idControlador,
                                    payloadJson.get("numeroAnel").asInt());
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeLiberarImposicao, getSelf());
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

                            default:
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;
                        }
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

    private boolean isPlanoConfigurado(Controlador controlador, int numeroAnel, int posicaoPlano) {
        Anel anel = controlador.getAneis().stream().filter(a -> a.getPosicao() == numeroAnel).findFirst().orElse(null);
        return anel != null && anel.getPlanos().stream().anyMatch(plano -> plano.getPosicao() == posicaoPlano);
    }

}

package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
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
public abstract class TransacaoActorHandler extends UntypedActor {
    protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    protected String idControlador;

    protected Storage storage;

    public TransacaoActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    protected abstract void executePrepareToCommit(Transacao transacao);

    protected abstract void executeCommit(Transacao transacao);

    protected abstract void executeAbort(Transacao transacao);

    @Override
//    public void onReceive(Object message) throws Exception {
//        if (message instanceof Envelope) {
//            Envelope envelope = (Envelope) message;
//            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
//                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
//                Transacao transacao = Transacao.fromJson(transacaoJson);
//                Controlador controlador;
//                JsonNode payloadJson;
//                JsonNode controladorJson;
//                log.info("DEVICE - TX Recebida: {}", transacao);
//                switch (transacao.etapaTransacao) {
//                    case PREPARE_TO_COMMIT:
//                        switch (transacao.tipoTransacao) {
//                            case PACOTE_PLANO:
//                                controlador = Controlador.isPacotePlanosValido(storage.getControladorJson(), transacao.payload);
//                                if (controlador != null) {
//                                    storage.setPlanos(Json.parse(transacao.payload.toString()));
//                                    storage.setControlador(controlador);
//                                    storage.setStatus(StatusDevice.ATIVO);
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case PACOTE_TABELA_HORARIA:
//                                controlador = Controlador.isPacoteTabelaHorariaValido(storage.getControladorJson(), transacao.payload);
//                                if (controlador != null) {
//                                    storage.setControladorStaging(controlador);
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case CONFIGURACAO_COMPLETA:
//                                payloadJson = Json.parse(transacao.payload.toString());
//                                JsonNode planoJson = payloadJson.get("pacotePlanos");
//                                //TODO: Validar com o Pedro
//                                JsonNode tabelaHorariaJson = payloadJson.get("pacoteTabelaHoraria");
//                                controladorJson = payloadJson.get("pacoteConfiguracao");
//                                controlador = Controlador.isPacoteCompletoValido(controladorJson, planoJson, tabelaHorariaJson);
//                                if (controlador != null) {
//                                    storage.setControlador(controlador);
//                                    storage.setPlanos(planoJson);
//                                    storage.setStatus(StatusDevice.ATIVO);
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case IMPOSICAO_MODO_OPERACAO:
//                                if (isImposicaoModoOperacaoOk(transacao)) {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case IMPOSICAO_PLANO:
//                                if (isImposicaoPlanoOk(storage.getControlador(), transacao)) {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case IMPOSICAO_PLANO_TEMPORARIO:
//                                controladorJson = savePlanoTemporario(storage.getControladorJson(), transacao);
//                                if (isImposicaoPlanoTemporarioOk(controladorJson, transacao)) {
//                                    controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
//                                    storage.setControlador(controlador);
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                }
//                                break;
//
//                            case LIBERAR_IMPOSICAO:
//                                payloadJson = Json.parse(transacao.payload.toString());
//                                if (payloadJson.get("numeroAnel").asInt() < 1) {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
//                                } else {
//                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                }
//                                break;
//
//                            default:
//                                transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
//                                break;
//                        }
//                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
//                        break;
//
//                    case COMMIT:
//                        switch (transacao.tipoTransacao) {
//                            case IMPOSICAO_MODO_OPERACAO:
//                                payloadJson = Json.parse(transacao.payload.toString());
//
//                                Envelope envelopeImposicaoModo = MensagemImposicaoModoOperacao.getMensagem(
//                                    idControlador,
//                                    payloadJson.get("modoOperacao").asText(),
//                                    payloadJson.get("numeroAnel").asInt(),
//                                    payloadJson.get("horarioEntrada").asLong(),
//                                    payloadJson.get("duracao").asInt());
//                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoModo, getSelf());
//
//                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
//                                break;
//
//                            case PACOTE_TABELA_HORARIA:
//                                payloadJson = Json.parse(transacao.payload.toString());
//                                // swefo rimediato, colocar 0
//                                boolean imediato = payloadJson.get("imediato").asBoolean();
//                                InfluuntLogger.log("[TRANSACAO HANDLER] onMudancaTabelaHoraria -- entrada");
//
//                                if (imediato) {
//                                    Envelope envelopeSinal = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA_IMEDIATAMENTE, idControlador, null);
//                                    getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeSinal, getSelf());
//                                }
//                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
//
//                                break;
//
//                            case IMPOSICAO_PLANO:
//                            case IMPOSICAO_PLANO_TEMPORARIO:
//                                payloadJson = Json.parse(transacao.payload.toString());
//                                Envelope envelopeImposicaoPlano = MensagemImposicaoPlano.getMensagem(
//                                    idControlador,
//                                    payloadJson.get("posicaoPlano").asInt(),
//                                    payloadJson.get("numeroAnel").asInt(),
//                                    payloadJson.get("horarioEntrada").asLong(),
//                                    payloadJson.get("duracao").asInt());
//                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeImposicaoPlano, getSelf());
//                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
//                                break;
//
//                            case LIBERAR_IMPOSICAO:
//                                payloadJson = Json.parse(transacao.payload.toString());
//                                Envelope envelopeLiberarImposicao = MensagemLiberarImposicao.getMensagem(
//                                    idControlador,
//                                    payloadJson.get("numeroAnel").asInt());
//                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeLiberarImposicao, getSelf());
//                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
//                                break;
//
//                            default:
//                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
//                                break;
//                        }
//                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
//                        break;
//
//                    case ABORT:
//                        transacao.etapaTransacao = EtapaTransacao.ABORTED;
//                        envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
//                        break;
//
//                    default:
//                        break;
//                }
//
//                log.info("DEVICE - TX Enviada: {}", transacao);
//                envelope.setConteudo(transacao.toJson().toString());
//                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
//            }
//        }
//    }


    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                log.info("DEVICE - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao) {
                    case PREPARE_TO_COMMIT:
                        executePrepareToCommit(transacao);
                        break;

                    case COMMIT:
                        executeCommit(transacao);
                        break;

                    case ABORT:
                        executeAbort(transacao);
                        transacao.etapaTransacao = EtapaTransacao.ABORTED;
                        break;

                    default:
                        break;
                }
                envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                envelope.setConteudo(transacao.toJson().toString());
                getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
                log.info("DEVICE - TX Enviada: {}", transacao);
            }
        }
    }
}

package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import json.ControladorCustomDeserializer;
import logger.InfluuntLogger;
import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;
import models.StatusDevice;
import org.joda.time.DateTime;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.*;
import scala.concurrent.duration.Duration;
import status.Transacao;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                Controlador controlador;
                JsonNode payloadJson;
                JsonNode controladorJson;
                log.info("DEVICE - TX Recebida: {}", transacao);
                switch (transacao.etapaTransacao) {
                    case PREPARE_TO_COMMIT:
                        switch (transacao.tipoTransacao) {
                            case PACOTE_PLANO:
                                controlador = Controlador.isPacotePlanosValido(storage.getControladorJson(), transacao.payload);
                                if (controlador != null) {
                                    storage.setControladorStaging(controlador);
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case PACOTE_TABELA_HORARIA:
                                controlador = Controlador.isPacoteTabelaHorariaValido(storage.getControladorJson(), transacao.payload);
                                System.out.println("Pacote Tabela Horária:\n" + transacao.payload.toString());
                                if (controlador != null) {
                                    storage.setControladorStaging(controlador);

                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case CONFIGURACAO_COMPLETA:
                                payloadJson = Json.parse(transacao.payload.toString());
                                JsonNode planoJson = payloadJson.get("pacotePlanos");
                                controladorJson = payloadJson.get("pacoteConfiguracao");
                                controlador = Controlador.isPacotePlanosValido(controladorJson, planoJson);
                                if (controlador != null) {
                                    storage.setControladorStaging(controlador);
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case IMPOSICAO_MODO_OPERACAO:
                                if (isImposicaoModoOperacaoOk(transacao)) {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case IMPOSICAO_PLANO:
                                if (isImposicaoPlanoOk(storage.getControlador(), transacao)) {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
                                }
                                break;

                            case IMPOSICAO_PLANO_TEMPORARIO:
                                controladorJson = savePlanoTemporario(storage.getControladorJson(), transacao);
                                if (isImposicaoPlanoTemporarioOk(controladorJson, transacao)) {
                                    controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
                                    storage.setControlador(controlador);
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_OK;
                                } else {
                                    transacao.etapaTransacao = EtapaTransacao.PREPARE_FAIL;
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
                            case PACOTE_PLANO:
                                InfluuntLogger.log("[TRANSACAO HANDLER] onTrocarPlanos -- entrada");
                                Envelope envelopeSinal = Sinal.getMensagem(TipoMensagem.TROCAR_PLANOS, idControlador, null);
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeSinal, getSelf());
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

                            case PACOTE_TABELA_HORARIA:
                                payloadJson = Json.parse(transacao.payload.toString());
                                boolean imediato = payloadJson.get("imediato").asBoolean();
                                InfluuntLogger.log("[TRANSACAO HANDLER] onMudancaTabelaHoraria -- entrada");

                                Envelope envelopeTH;
                                if (imediato) {
                                    envelopeTH = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA_IMEDIATAMENTE, idControlador, null);
                                } else {
                                    envelopeTH = Sinal.getMensagem(TipoMensagem.TROCAR_TABELA_HORARIA, idControlador, null);
                                }
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeTH, getSelf());
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

                            case CONFIGURACAO_COMPLETA:
                                Envelope envelopeConfig = Sinal.getMensagem(TipoMensagem.ATUALIZAR_CONFIGURACAO, idControlador, null);
                                getContext().actorSelection(AtoresDevice.motor(idControlador)).tell(envelopeConfig, getSelf());
                                transacao.etapaTransacao = EtapaTransacao.COMMITED;
                                break;

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
                            case IMPOSICAO_PLANO_TEMPORARIO:
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

    private boolean isImposicaoPlanoOk(Controlador controlador, Transacao transacao) {
        JsonNode payload = Json.parse(transacao.payload.toString());
        int posicaoPlano = payload.get("posicaoPlano").asInt();
        int numeroAnel = payload.get("numeroAnel").asInt();
        Long horarioEntrada = payload.get("horarioEntrada").asLong();
        int duracao = payload.get("duracao").asInt();
        boolean planoConfigurado = isPlanoConfigurado(controlador, numeroAnel, posicaoPlano);

        return posicaoPlano >= 0 && numeroAnel >= 1 && duracao >= 15 && duracao <= 600 && planoConfigurado && horarioEntrada > System.currentTimeMillis();
    }

    private boolean isImposicaoModoOperacaoOk(Transacao transacao) {
        try {
            JsonNode payload = Json.parse(transacao.payload.toString());
            ModoOperacaoPlano.valueOf(payload.get("modoOperacao").asText());
            int numeroAnel = payload.get("numeroAnel").asInt();
            Long horarioEntrada = payload.get("horarioEntrada").asLong();
            int duracao = payload.get("duracao").asInt();
            return numeroAnel >= 1 && duracao >= 15 && duracao <= 600 && horarioEntrada > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isImposicaoPlanoTemporarioOk(JsonNode controladorJson, Transacao transacao) {
        Controlador controlador = new ControladorCustomDeserializer().getControladorFromJson(controladorJson);
        return isImposicaoPlanoOk(controlador, transacao);
    }

    private JsonNode savePlanoTemporario(JsonNode controladorJson, Transacao transacao) {
        JsonNode payloadJson = Json.parse(transacao.payload.toString());
        JsonNode planoTemporarioJson = payloadJson.get("plano");

        // add estagios planos
        for (JsonNode epJson : planoTemporarioJson.get("estagiosPlanos")) {
            ((ArrayNode) controladorJson.get("estagiosPlanos")).add(epJson);
        }

        // add grupos semaforicos planos
        for (JsonNode gspJson : planoTemporarioJson.get("gruposSemaforicosPlanos")) {
            ((ArrayNode) controladorJson.get("gruposSemaforicosPlanos")).add(gspJson);
        }

        //  add versão plano
        ((ArrayNode) controladorJson.get("versoesPlanos")).add(planoTemporarioJson.get("versaoPlano"));

        // add plano
        for (Iterator<JsonNode> it = controladorJson.get("planos").iterator(); it.hasNext(); ) {
            JsonNode plano = it.next();
            boolean mesmaPosicao = plano.get("posicao").asInt() == planoTemporarioJson.get("posicao").asInt();
            boolean mesmoAnel = plano.get("anel").get("idJson").asText().equals(planoTemporarioJson.get("anel").get("idJson").asText());
            if (mesmaPosicao && mesmoAnel) {
                it.remove();
                break;
            }
        }
        ((ArrayNode) controladorJson.get("planos")).add(planoTemporarioJson);

        return controladorJson;
    }
}

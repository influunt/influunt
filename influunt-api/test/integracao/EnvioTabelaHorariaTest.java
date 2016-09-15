package integracao;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import models.Anel;
import models.Controlador;
import models.StatusControlador;
import models.StatusDevice;
import org.junit.Test;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import server.Central;
import server.conn.CentralMessageBroker;
import status.StatusControladorFisico;
import status.Transacao;
import utils.AtoresCentral;

import javax.validation.groups.Default;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Test
    public void configuracaoValida() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Test
    public void enviarPlanosOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        Transacao transacao = new Transacao(idControlador, null);
        String transacaoJson = Json.toJson(transacao).toString();
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, idControlador, null, 1, transacaoJson, null);

        ActorSystem context = provideApp.injector().instanceOf(ActorSystem.class);
        ActorRef actor = context.actorOf(Props.create(CentralMessageBroker.class), "MessageBrokerTeste");
        actor.tell(envelope, null);

        await().until(() -> onPublishFutureList.size() > 9);

        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(5)));
        JsonNode jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

        String idTransacao = jsonConteudo.get("transacaoId").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(6)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.PREPARE_OK.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(7)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(8)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.COMMITED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(9)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.COMPLETED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());
    }

    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        controlador = new ControladorHelper().getControlador();
        await().until(() -> onPublishFutureList.size() > 4);

        Transacao transacao = new Transacao(idControlador, null);
        String transacaoJson = Json.toJson(transacao).toString();
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, idControlador, null, 1, transacaoJson, null);

        ActorSystem context = provideApp.injector().instanceOf(ActorSystem.class);
        ActorRef actor = context.actorOf(Props.create(CentralMessageBroker.class), "MessageBrokerTeste");
        actor.tell(envelope, null);

        await().until(() -> onPublishFutureList.size() > 8);

        JsonNode json = play.libs.Json.parse(new String(onPublishFutureList.get(5)));
        JsonNode jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.PREPARE_TO_COMMIT.toString(), jsonConteudo.get("etapaTransacao").asText());

        String idTransacao = jsonConteudo.get("transacaoId").asText();

        json = play.libs.Json.parse(new String(onPublishFutureList.get(6)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.PREPARE_FAIL.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(7)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.ABORT.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(8)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
        assertEquals(idControlador, json.get("idControlador").asText());
        assertEquals(EtapaTransacao.ABORTED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

        json = play.libs.Json.parse(new String(onPublishFutureList.get(9)));
        jsonConteudo = play.libs.Json.parse(json.get("conteudo").asText());
        assertEquals(TipoMensagem.TRANSACAO.toString(), json.get("tipoMensagem").asText());
    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
                Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}

package integracao;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import checks.Erro;
import checks.InfluuntValidator;
import checks.PlanosCheck;
import checks.TabelaHorariosCheck;
import com.fasterxml.jackson.databind.JsonNode;
import json.ControladorCustomSerializer;
import models.Controlador;
import org.junit.Ignore;
import org.junit.Test;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import protocol.TipoTransacao;
import server.conn.CentralMessageBroker;
import status.Transacao;

import javax.validation.groups.Default;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rodrigosol on 6/22/16.
 */
public class EnvioTabelaHorariaTest extends BasicMQTTTest {

    @Ignore
    @Test
    public void configuracaoValida() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        List<Erro> erros = getErros(controlador);
        assertThat(erros, org.hamcrest.Matchers.empty());
    }

    @Ignore
    @Test
    public void enviarPlanosOK() {
        startClient();
        controlador = new ControladorHelper().setPlanos(controlador);
        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Transacao transacao = new Transacao(idControlador, pacotePlanosJson, TipoTransacao.PACOTE_PLANO);

        String transacaoJson = transacao.toJson().toString();
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

    @Ignore
    @Test
    public void enviarPlanosNaoOK() {
        startClient();
        controlador = new ControladorHelper().getControlador();
        await().until(() -> onPublishFutureList.size() > 4);

        JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
        Transacao transacao = new Transacao(idControlador, pacotePlanosJson, TipoTransacao.PACOTE_PLANO);

        String transacaoJson = transacao.toJson().toString();
        Envelope envelope = new Envelope(TipoMensagem.TRANSACAO, idControlador, null, 1, transacaoJson, null);

        ActorSystem context = provideApp.injector().instanceOf(ActorSystem.class);
        ActorRef actor = context.actorOf(Props.create(CentralMessageBroker.class), "MessageBrokerTeste");
        actor.tell(envelope, null);

        await().until(() -> onPublishFutureList.size() > 7);

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
        assertEquals(EtapaTransacao.FAILED.toString(), jsonConteudo.get("etapaTransacao").asText());
        assertEquals(idTransacao, jsonConteudo.get("transacaoId").asText());

    }


    protected List<Erro> getErros(Controlador controlador) {
        return new InfluuntValidator<Controlador>().validate(controlador,
            Default.class, PlanosCheck.class, TabelaHorariosCheck.class);
    }

}

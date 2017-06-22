package controllers.central;

import be.objectify.deadbolt.java.actions.Dynamic;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.fusesource.mqtt.client.QoS;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.TransacaoHelperCentral;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by pedropires on 1/24/17.
 */
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class TransacoesController extends Controller {

    @Inject
    private TransacaoHelperCentral transacaoHelper;

    @Transactional
    public CompletionStage<Result> enviar() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        JsonNode pacoteTransacao = json.get("pacoteTransacao");
        QoS qos = QoS.valueOf(json.get("qos").asText());
        String pacoteTransacaoId = transacaoHelper.sendTransaction(pacoteTransacao, qos);
        return CompletableFuture.completedFuture(ok(pacoteTransacaoId));
    }

    @Transactional
    public CompletionStage<Result> lerDados() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        String controladorFisicoId = json.get("controladorFisicoId").asText();
        transacaoHelper.lerDados(controladorFisicoId);
        return CompletableFuture.completedFuture(ok(controladorFisicoId));
    }
}

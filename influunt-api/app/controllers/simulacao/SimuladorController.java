package controllers.simulacao;

import akka.actor.ActorSystem;
import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.AreasCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Area;
import models.Controlador;
import models.ModoOperacaoPlano;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import simulador.akka.GerenciadorDeSimulacoes;
import simulador.parametros.ParametroSimulacao;
import status.ModoOperacaoControlador;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class SimuladorController extends Controller {

    @Inject
    private GerenciadorDeSimulacoes simulacoes;


    public CompletionStage<Result> simular(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ParametroSimulacao params = Json.fromJson(json, ParametroSimulacao.class);
        params.setControlador(Controlador.find.byId(params.getIdControlador()));
        simulacoes.iniciarSimulacao(params);

        return CompletableFuture.completedFuture(ok(Json.toJson(params.getSimulacaoConfig())));
    }

}

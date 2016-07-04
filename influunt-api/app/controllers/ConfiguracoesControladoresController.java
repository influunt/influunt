package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.ConfiguracaoControlador;
import models.ModeloControlador;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class ConfiguracoesControladoresController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(ConfiguracaoControlador.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ConfiguracaoControlador configuracaoControlador = Json.fromJson(json, ConfiguracaoControlador.class);
        List<Erro> erros = new InfluuntValidator<ConfiguracaoControlador>().validate(configuracaoControlador);

        if(erros.isEmpty()) {
            configuracaoControlador.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
        }else{
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }else{

            configuracaoControlador = Json.fromJson(json, ConfiguracaoControlador.class);
            configuracaoControlador.setId(UUID.fromString(id));
            List<Erro> erros = new InfluuntValidator<ConfiguracaoControlador>().validate(configuracaoControlador);

            if(erros.isEmpty()) {
                configuracaoControlador.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
            }else{
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }

        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(configuracaoControlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        ConfiguracaoControlador configuracaoControlador = ConfiguracaoControlador.find.byId(UUID.fromString(id));
        if (configuracaoControlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            configuracaoControlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }
}

package controllers;

import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Perfil;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class PerfisController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Perfil perfil = Json.fromJson(json, Perfil.class);

            List<Erro> erros = new InfluuntValidator<Perfil>().validate(perfil);

            if (erros.isEmpty()) {
                perfil.save();
                return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }

        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Perfil perfil = Perfil.find.byId(UUID.fromString(id));
        if (perfil == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            perfil.getPermissoes();
            return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Perfil.class, request().queryString()).query());
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Perfil perfil = Perfil.find.byId(UUID.fromString(id));
        if (perfil == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (perfil.getUsuarios().isEmpty()) {
            perfil.delete();
            return CompletableFuture.completedFuture(ok());
        } else {
            Erro erro = new Erro("Perfil", "Esse perfil não pode ser removido, pois existe(m) usuário(s) vinculado(s) ao mesmo.", "");
            return CompletableFuture.completedFuture(
                status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(erro)))
            );
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }
        Perfil perfilExistente = Perfil.find.byId(UUID.fromString(id));
        if (perfilExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Perfil perfil = Json.fromJson(json, Perfil.class);
        perfil.setId(UUID.fromString(id));
        List<Erro> erros = new InfluuntValidator<Perfil>().validate(perfil);

        if (erros.isEmpty()) {
            perfil.update();
            return CompletableFuture.completedFuture(ok(Json.toJson(perfil)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

}

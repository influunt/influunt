package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.CidadesCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Cidade;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class CidadesController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {

        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Cidade cidade = Json.fromJson(json, Cidade.class);
        List<Erro> erros = new InfluuntValidator<Cidade>().validate(cidade, javax.validation.groups.Default.class, CidadesCheck.class);

        if (erros.isEmpty()) {
            cidade.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        InfluuntResultBuilder result;
        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Cidade.class, request().queryString()).query());
        } else {
            String[] cidadeId = {u.getArea().getCidade().getId().toString()};
            Map<String, String[]> params = new HashMap<>();
            params.putAll(ctx().request().queryString());
            params.put("id", cidadeId);
            result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Cidade.class, params).query());
        }
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {

            if (cidade.getAreas().isEmpty()) {
                cidade.delete();
                return CompletableFuture.completedFuture(ok());
            } else {
                Erro erro = new Erro("Cidade", "Essa cidade não pode ser removida, pois existe(m) área(s) vinculada(s) à mesma.", "");
                return CompletableFuture.completedFuture(
                    status(UNPROCESSABLE_ENTITY, Json.toJson(Collections.singletonList(erro)))
                );
            }
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Cidade cidade = Cidade.find.byId(UUID.fromString(id));
        if (cidade == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            cidade = Json.fromJson(json, Cidade.class);
            cidade.setId(UUID.fromString(id));

            List<Erro> erros = new InfluuntValidator<Cidade>().validate(cidade, javax.validation.groups.Default.class, CidadesCheck.class);
            if (erros.isEmpty()) {
                cidade.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(cidade)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }

    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }
}

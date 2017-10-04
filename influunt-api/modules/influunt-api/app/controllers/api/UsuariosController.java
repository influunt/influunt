package controllers.api;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Sessao;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class UsuariosController extends Controller {

    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Usuario usuario = Json.fromJson(json, Usuario.class);
        List<Erro> erros = new InfluuntValidator<Usuario>().validate(usuario);
        if (erros.isEmpty()) {
            if (Usuario.find.where().ieq("login", usuario.getLogin()).findRowCount() != 0) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(
                    Collections.singletonList(new Erro("usuario", "login já utilizado", "login"))))
                );
            } else {
                usuario.save();
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            }
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Usuario usuario = Usuario.find.byId(UUID.fromString(id));
        if (usuario == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
        }
    }

    public CompletionStage<Result> findAll() {
        InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Usuario.class, request().queryString()).fetch(Collections.singletonList("area")).query());
        return CompletableFuture.completedFuture(ok(result.toJson()));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Usuario usuario = Usuario.find.byId(UUID.fromString(id));
        if (usuario == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            usuario.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        }

        Usuario usuario = Usuario.find.fetch("area").where().eq("id", id).findUnique();
        if (usuario == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            boolean root = usuario.isRoot();
            usuario = Json.fromJson(json, Usuario.class);
            usuario.setId(UUID.fromString(id));
            usuario.setRoot(root);
            List<Erro> erros = new InfluuntValidator<Usuario>().validate(usuario);

            if (erros.isEmpty()) {
                usuario.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(usuario)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }

    }

    @Transactional
    public CompletionStage<Result> accessLog(String id) {
        HashMap<String, String[]> params = new HashMap<>();
        Usuario usuario = Usuario.find.byId(UUID.fromString(id));
        if (usuario == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            String[] usuarioId = {id};
            params.putAll(request().queryString());
            params.put("usuario_id", usuarioId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Sessao.class, params).query());
            return CompletableFuture.completedFuture(ok(result.toJson()));
        }
    }

}

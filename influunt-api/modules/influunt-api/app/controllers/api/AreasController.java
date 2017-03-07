package controllers.api;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.AreasCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.Usuario;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import utils.InfluuntQueryBuilder;
import utils.InfluuntResultBuilder;

import javax.validation.groups.Default;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class AreasController extends Controller {


    @Transactional
    public CompletionStage<Result> create() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Area area = Json.fromJson(json, Area.class);
            List<Erro> erros = new InfluuntValidator<Area>().validate(area, Default.class, AreasCheck.class);

            if (erros.isEmpty()) {
                area.save();
                area.refresh();
                return CompletableFuture.completedFuture(ok(Json.toJson(area)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Area area = Area.find.fetch("cidade").where().eq("id", UUID.fromString(id)).findUnique();
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        Usuario u = getUsuario();
        Result res;
        Map<String, String[]> params = new HashMap<>();
        params.putAll(ctx().request().queryString());

        if (u.isRoot() || u.podeAcessarTodasAreas()) {
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Area.class, params).fetch(Collections.singletonList("cidade")).query());
            res = ok(result.toJson());
        } else if (u.getArea() != null) {
            String[] areaId = {u.getArea().getId().toString()};
            params.put("id", areaId);
            InfluuntResultBuilder result = new InfluuntResultBuilder(new InfluuntQueryBuilder(Area.class, params).fetch(Collections.singletonList("cidade")).query());

            res = ok(result.toJson());
        } else {
            res = forbidden();
        }

        return CompletableFuture.completedFuture(res);
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        Area area = Area.find.byId(UUID.fromString(id));
        if (area == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        if (area.getControladores().isEmpty() && area.getSubareas().isEmpty()) {
            area.delete();
            return CompletableFuture.completedFuture(ok());
        } else {
            Erro erro = new Erro("Area", "Essa área não pode ser removida, pois existe(m) controlador(es) ou subárea(s) vinculado(s) à mesma.", "");
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
        Area areaExistente = Area.find.byId(UUID.fromString(id));
        if (areaExistente == null) {
            return CompletableFuture.completedFuture(notFound());
        }

        Area area = Json.fromJson(json, Area.class);
        area.setId(areaExistente.getId());
        List<Erro> erros = new InfluuntValidator<Area>().validate(area, Default.class, AreasCheck.class);

        if (erros.isEmpty()) {
            area.update();
            area.refresh();
            return CompletableFuture.completedFuture(ok(Json.toJson(area)));
        } else {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        }

    }

    private Usuario getUsuario() {
        return (Usuario) ctx().args.get("user");
    }
}

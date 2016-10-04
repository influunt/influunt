package controllers.simulacao;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import checks.AreasCheck;
import checks.Erro;
import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import models.Area;
import models.ModoOperacaoPlano;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import status.ModoOperacaoControlador;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
@DeferredDeadbolt
@Security.Authenticated(Secured.class)
@Dynamic("Influunt")
public class SimualdorController extends Controller {

    public CompletionStage<Result> simular(String id) {

        JsonNode json = request().body().asJson();

        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {

            Area area = Json.fromJson(json, Area.class);
            List<Erro> erros = new InfluuntValidator<Area>().validate(area, javax.validation.groups.Default.class, AreasCheck.class);

            if (erros.isEmpty()) {
                area.save();
                area.refresh();
                return CompletableFuture.completedFuture(ok(Json.toJson(area)));
            } else {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }
        }
    }

}

package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import models.Permissao;
import models.PermissaoApp;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PermissoesAppController extends Controller {

    @Transactional
    public CompletionStage<Result> findAll() {
        List<PermissaoApp> permissoesApp = PermissaoApp.find.fetch("permissoes").findList();
        List<Permissao> permissoes = Permissao.find.findList();
        Map<String, List> jsonMap = new HashMap<>();
        jsonMap.put("permissoes", permissoes);
        jsonMap.put("permissoesApp", permissoesApp);
        JsonNode json = Json.toJson(jsonMap);
        return CompletableFuture.completedFuture(ok(json));
    }

}

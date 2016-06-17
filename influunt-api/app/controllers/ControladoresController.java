package controllers;

import checks.InfluuntValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.EntityNotFound;
import models.Anel;
import models.Controlador;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ControladoresController extends Controller {


    @Inject
    FormFactory formFactory;

    @Transactional
    public CompletionStage<Result> create() {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }
        Controlador controlador = Json.fromJson(request().body().asJson(),Controlador.class);
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador);
        if (erros.size() > 0) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
        } else {
            controlador.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(Long id) {
        Controlador controlador = Controlador.find.byId(id);
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(Controlador.find.findList())));
    }

    @Transactional
    public CompletionStage<Result> delete(Long id) {
        Controlador controlador = Controlador.find.byId(id);
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    public CompletionStage<Result> update(Long id) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }
        Controlador controlador = Controlador.find.byId(id);
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }else{
            controlador = Json.fromJson(request().body().asJson(),Controlador.class);
            List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador);

            if (!erros.isEmpty()) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, Json.toJson(erros)));
            }else{
                controlador.setId(id);
                controlador.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
            }

        }
    }

}
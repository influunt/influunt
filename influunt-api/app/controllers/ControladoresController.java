package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.EntityNotFound;
import models.Controlador;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ControladorCrudService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ControladoresController extends Controller {

    @Inject
    private ControladorCrudService controladorService;

    @Inject
    FormFactory formFactory;

    @Transactional
    public CompletionStage<Result> create() {

        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }

        Form<Controlador> form = formFactory.form(Controlador.class).bind(request().body().asJson());
        Logger.info("Form Recebido:" + form.toString());

        if (form.hasErrors()) {
            return CompletableFuture.completedFuture(status(422, form.errorsAsJson()));
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(controladorService.save(form.get()))));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Controlador controlador = controladorService.findOne(id);
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findAll() {
        return CompletableFuture.completedFuture(ok(Json.toJson(controladorService.findAll())));
    }

    @Transactional
    public CompletionStage<Result> delete(String id) {
        try {
            controladorService.delete(id);
        } catch (EntityNotFound e) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting Json data"));
        } else {
            Controlador controlador = Json.fromJson(json, Controlador.class);
            if (controlador.getId() == null) {
                return CompletableFuture.completedFuture(notFound());
            }
            controladorService.update(controlador, id);
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

}
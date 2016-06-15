package controllers;

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
        Form<Controlador> form = formFactory.form(Controlador.class).bind(request().body().asJson());
        Iterator<JsonNode> aneisIterator = request().body().asJson().get("aneis").iterator();
        List<Anel> aneis = new ArrayList<Anel>();
        while (aneisIterator.hasNext()){
         aneis.add(Json.fromJson(aneisIterator.next(),Anel.class));
        }

        //((Controlador) form.field()).setAneis(aneis);


        Logger.debug(form.toString());
        if (form.hasErrors()) {
            return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, form.errorsAsJson()));
        } else {
            Controlador controlador = form.get();
            controlador.save();
            return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
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
    public CompletionStage<Result> delete(String id) {
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        } else {
            controlador.delete();
            return CompletableFuture.completedFuture(ok());
        }
    }

    @Transactional
    public CompletionStage<Result> update(String id) {
        if (request().body() == null) {
            return CompletableFuture.completedFuture(badRequest());
        }
        Controlador controlador = Controlador.find.byId(UUID.fromString(id));
        if (controlador == null) {
            return CompletableFuture.completedFuture(notFound());
        }else{
            Form<Controlador> form = formFactory.form(Controlador.class).bind(request().body().asJson());

            if (form.hasErrors()) {
                return CompletableFuture.completedFuture(status(UNPROCESSABLE_ENTITY, form.errorsAsJson()));
            }else{
                controlador = form.get();
                controlador.setId(UUID.fromString(id));
                controlador.update();
                return CompletableFuture.completedFuture(ok(Json.toJson(controlador)));
            }

        }
    }

}
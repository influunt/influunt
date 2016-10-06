package controllers;

import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Dynamic;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import models.Anel;
import models.Imagem;
import net.coobird.thumbnailator.Thumbnails;
import play.Application;
import play.Logger;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DeferredDeadbolt
public class ImagensController extends Controller {

    @Inject
    private Provider<Application> provider;


    @Transactional
    @Security.Authenticated(Secured.class)
    @Dynamic("Influunt")
    public CompletionStage<Result> create() {
        return CompletableFuture.completedFuture(badRequest());
        // Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        // Http.MultipartFormData.FilePart<File> picture = body.getFile("imagem");
        // String anelIdJson = body.asFormUrlEncoded().get("anelIdJson")[0];
        // if (picture != null) {
        //     Imagem imagem = new Imagem();
        //     imagem.setFilename(picture.getFilename());
        //     imagem.setContentType(picture.getContentType());
        //     imagem.save();

        //     File tmpFile = picture.getFile();
        //     try {

        //         File appRootPath = provider.get().path();
        //         Files.copy(tmpFile, imagem.getPath(appRootPath));

        //         //Create thumbnail
        //         Thumbnails.of(tmpFile)
        //                 .forceSize(150, 150)
        //                 .outputFormat("jpg")
        //                 .toFile(imagem.getPath(appRootPath, "thumb"));

        //     } catch (IOException e) {
        //         imagem.delete();
        //         Logger.error(e.getMessage(), e);
        //         return CompletableFuture.completedFuture(internalServerError());
        //     }

        //     Map<String, Object> response = new HashMap<>();
        //     response.put("imagem", imagem);
        //     response.put("anelIdJson", anelIdJson);
        //     return CompletableFuture.completedFuture(ok(Json.toJson(response)));
        // } else {
        //     return CompletableFuture.completedFuture(badRequest());
        // }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Imagem imagem = Imagem.find.byId(UUID.fromString(id));
        if (imagem == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        File appRootPath = provider.get().path();
        File path = path = imagem.getPath(appRootPath);
        return CompletableFuture.completedFuture(ok(path).as(imagem.getContentType()));
    }

    @Transactional
    public CompletionStage<Result> findOneVersion(String id, String version) {
        Imagem imagem = Imagem.find.byId(UUID.fromString(id));
        if (imagem == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        File appRootPath = provider.get().path();
        File path = imagem.getPath(appRootPath, version.concat(".jpg"));
        return CompletableFuture.completedFuture(ok(path).as(imagem.getContentType()));
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    @Dynamic("Influunt")
    public CompletionStage<Result> delete(String id) {
        Imagem imagem = Imagem.find.byId(UUID.fromString(id));
        if (imagem == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        imagem.apagar(provider.get().path());
        return CompletableFuture.completedFuture(ok());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    @Dynamic("Influunt")
    public CompletionStage<Result> deleteCroqui(String id) {
        Imagem imagem = Imagem.find.byId(UUID.fromString(id));
        if (imagem == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        Anel anel = Anel.find.where().eq("croqui_id", imagem.getId()).findUnique();
        if (anel != null) {
            anel.setCroqui(null);
            anel.update();
            imagem.apagar(provider.get().path());
        }
        return CompletableFuture.completedFuture(ok());
    }
}

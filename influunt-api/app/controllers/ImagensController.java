package controllers;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import models.Imagem;
import play.Application;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ImagensController extends Controller {

    @Inject
    private Provider<Application> provider;


    @Transactional
    public CompletionStage<Result> create() {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("imagem");
        if (picture != null) {
            Imagem imagem = new Imagem();
            imagem.setFilename(picture.getFilename());
            imagem.setContentType(picture.getContentType());
            imagem.save();

            File tmpFile = picture.getFile();
            try {
                File appRootPath = provider.get().path();
                Files.copy(tmpFile, imagem.getPath(appRootPath));
            } catch (IOException e) {
                imagem.delete();
                return CompletableFuture.completedFuture(internalServerError());
            }
            return CompletableFuture.completedFuture(ok());
        } else {
            return CompletableFuture.completedFuture(badRequest());
        }
    }

    @Transactional
    public CompletionStage<Result> findOne(String id) {
        Imagem imagem = Imagem.find.byId(UUID.fromString(id));
        if (imagem == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        File appRootPath = provider.get().path();
        File path = imagem.getPath(appRootPath);
        return CompletableFuture.completedFuture(ok(path).as(imagem.getContentType()));
    }
}

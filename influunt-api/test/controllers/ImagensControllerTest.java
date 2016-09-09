package controllers;

import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.google.inject.Singleton;
import models.Imagem;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.DataPart;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;


/**
 * Created by pedropires on 6/17/16.
 */
public class ImagensControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

    @Before
    public void setup() {
        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);
    }

    @Test
    public void testCriarNovaImagem() {
        File appRootPath = app.path();
        File imagem = new File(appRootPath, "/test/resources/ubuntu.jpeg");
        // Necessário para o teste passar caso esteja sendo rodado
        // diretamente no IntelliJ
        if (!imagem.exists()) {
            imagem = new File(appRootPath, "../../test/resources/ubuntu.jpeg");
        }
        Materializer mat = app.injector().instanceOf(Materializer.class);
        DataPart dataPart = new DataPart("hello", "world");
        Source<ByteString, ?> src = FileIO.fromFile(imagem);
        FilePart<Source<ByteString, ?>> filePart = new FilePart<>("imagem", "ubuntu.jpeg", "image/jpeg", src);

        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .uri(routes.ImagensController.create().url()).bodyMultipart(Arrays.asList(dataPart, filePart), mat);
        Result result = route(request);

        assertEquals(OK, result.status());
    }

    @Test
    public void testBuscarImagem() {
        Imagem imagem = new Imagem();
        imagem.setContentType("image/jpeg");
        imagem.setFilename("ubuntu.jpeg");
        imagem.save();

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET")
                .uri(routes.ImagensController.findOne(imagem.getId().toString()).url());
        Result result = route(request);

        assertEquals(200, result.status());
        assertTrue(result.contentType().toString().contains("image/jpeg"));
    }

    @Test
    public void testApagarImagemExistente() {
        Imagem imagem = new Imagem();
        imagem.setContentType("image/jpeg");
        imagem.setFilename("ubuntu.jpeg");
        imagem.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ImagensController.delete(imagem.getId().toString()).url());
        Result result = route(deleteRequest);

        assertEquals(200, result.status());
        assertNull(Imagem.find.byId(imagem.getId()));
    }

    @Test
    public void testApagarImagemNaoExistente() {
        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
                .uri(routes.ImagensController.delete(UUID.randomUUID().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(404, result.status());
    }


}

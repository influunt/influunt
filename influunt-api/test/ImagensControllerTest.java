import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import controllers.routes;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.DataPart;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.test.WithApplication;
import models.Imagem;
import security.Authenticator;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;


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
                .overrides(bind(Authenticator.class).to(TestAuthenticator.class)).in(Mode.TEST).build();
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

}

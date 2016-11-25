package controllers;

import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.DataPart;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;


/**
 * Created by pedropires on 6/17/16.
 */
public class ImagensControllerTest extends WithInfluuntApplicationNoAuthentication {

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
        DataPart dataPart = new DataPart("anelIdJson", "1234");
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

    @Test
    public void testApagarCroquiAnel() {
        Imagem imagem = new Imagem();
        imagem.setContentType("image/jpeg");
        imagem.setFilename("ubuntu.jpeg");
        imagem.save();

        Anel anel = new Anel();
        anel.setCroqui(imagem);
        anel.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.ImagensController.deleteCroqui(imagem.getId().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(200, result.status());

        assertNull(Imagem.find.byId(imagem.getId()));
        assertNull(Anel.find.where().eq("croqui_id", imagem.getId()).findUnique());
    }

    @Test
    public void testApagarCroquiControlador() {
        Imagem imagem = new Imagem();
        imagem.setContentType("image/jpeg");
        imagem.setFilename("ubuntu.jpeg");
        imagem.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();
        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();
        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();
        ControladorTestUtil controladorTestUtils = new ControladorTestUtil(area, null, fabricante, modeloControlador);

        Controlador controlador = controladorTestUtils.getControladorDadosBasicos();
        controlador.setCroqui(imagem);
        controlador.save();

        Http.RequestBuilder deleteRequest = new Http.RequestBuilder().method("DELETE")
            .uri(routes.ImagensController.deleteCroqui(imagem.getId().toString()).url());
        Result result = route(deleteRequest);
        assertEquals(200, result.status());

        assertNull(Imagem.find.byId(imagem.getId()));
        assertNull(Controlador.find.where().eq("croqui_id", imagem.getId()).findUnique());
    }
}

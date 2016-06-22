package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.ControladorHelper;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import models.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;

public class HelpersControllerTest extends WithApplication {


    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration).in(Mode.TEST).build();
    }

    @Test
    public void testControladorHelper() {
        Cidade bh = new Cidade();
        bh.setNome("Belo Horizonte");
        bh.save();

        Area sul = new Area();
        sul.setDescricao(2);
        sul.setCidade(bh);
        sul.save();

        Area norte = new Area();
        norte.setDescricao(2);
        norte.setCidade(bh);
        norte.save();

        Cidade rio = new Cidade();
        rio.setNome("Rio de Janeiro");
        rio.save();


        Fabricante raro = new Fabricante();
        raro.setNome("Raro");
        raro.save();

        ConfiguracaoControlador conf = new ConfiguracaoControlador();
        conf.save();

        ModeloControlador m1 = new ModeloControlador();
        m1.setFabricante(raro);
        m1.setConfiguracao(conf);
        m1.save();

        Fabricante labs = new Fabricante();
        labs.setNome("Labs");
        labs.save();


        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("GET")
                .uri(routes.HelpersController.controladorHelper().url());
        Result postResult = route(postRequest);
        assertEquals(OK, postResult.status());
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Logger.debug(json.toString());
        ControladorHelper ch =  Json.fromJson(json,ControladorHelper.class);

        assertEquals(2,ch.getCidades().size());
        assertEquals(2,ch.getCidades().get(0).getAreas().size());
        assertEquals(2,ch.getFabricantes().size());
        assertNotNull(ch.getFabricantes().get(0).getModelos());
        assertNotNull(ch.getFabricantes().get(0).getModelos().get(0).getConfiguracao());

    }





}

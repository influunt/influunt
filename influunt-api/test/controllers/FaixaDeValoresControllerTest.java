package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import models.FaixasDeValores;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 04/10/16.
 */
public class FaixaDeValoresControllerTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void deveriaCriarFaixaDeValores() {
        FaixasDeValores faixasDeValores = FaixasDeValores.getInstance();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.FaixasDeValoresController.update().url()).bodyJson(Json.toJson(faixasDeValores));
        Result postResult = route(postRequest);
        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        FaixasDeValores faixaRetornada = Json.fromJson(json, FaixasDeValores.class);

        assertEquals(200, postResult.status());
        assertNotNull(faixaRetornada.getId());
    }

    @Test
    public void naoDeveriaCriarFaixaDeValoresComTempoPermanenciaEstagioVeicularForaDaFaixaDeIntervaloMenorQueTempoMinimo() {
        FaixasDeValores faixasDeValores = FaixasDeValores.getInstance();
        faixasDeValores.setDefaultTempoMaximoPermanenciaEstagioVeicular(20);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.FaixasDeValoresController.update().url()).bodyJson(Json.toJson(faixasDeValores));
        Result postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());
    }

    @Test
    public void naoDeveriaCriarFaixaDeValoresComTempoPermanenciaEstagioVeicularForaDaFaixaDeIntervaloMaiorQueTempoMaximo() {
        FaixasDeValores faixasDeValores = FaixasDeValores.getInstance();
        faixasDeValores.setDefaultTempoMaximoPermanenciaEstagioVeicular(2000);

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("PUT")
                .uri(routes.FaixasDeValoresController.update().url()).bodyJson(Json.toJson(faixasDeValores));
        Result postResult = route(postRequest);
        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());
    }
}

package models;

import checks.*;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import org.hamcrest.Matchers;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 6/28/16.
 */
public class ControladorTransicoesProibidasTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);

        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);
        assertThat(erros, Matchers.empty());
    }

    @Test
    public void testWithErrors() {
        Controlador controlador = getControladorVerdesConflitantes();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();


        Estagio estagio1AnelCom2Estagios = anelCom2Estagios.getEstagios().get(0);
        estagio1AnelCom2Estagios.setDescricao("estagio1AnelCom2Estagios");
        Estagio estagio2AnelCom2Estagios = anelCom2Estagios.getEstagios().get(1);
        estagio2AnelCom2Estagios.setDescricao("estagio2AnelCom2Estagios");

        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().get(0);
        estagio1AnelCom4Estagios.setDescricao("estagio1AnelCom4Estagios");
        Estagio estagio2AnelCom4Estagios = anelCom4Estagios.getEstagios().get(1);
        estagio2AnelCom4Estagios.setDescricao("estagio2AnelCom4Estagios");
        Estagio estagio3AnelCom4Estagios = anelCom4Estagios.getEstagios().get(2);
        estagio3AnelCom4Estagios.setDescricao("estagio3AnelCom4Estagios");
        Estagio estagio4AnelCom4Estagios = anelCom4Estagios.getEstagios().get(3);
        estagio4AnelCom4Estagios.setDescricao("estagio4AnelCom4Estagios");

        TransicaoProibida transicaoProibida = new TransicaoProibida();
        transicaoProibida.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibida.setDestino(null);
        transicaoProibida.setAlternativo(null);

        estagio1AnelCom4Estagios.setOrigemDeTransicoesProibidas(Arrays.asList(transicaoProibida));

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);

        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "não pode ficar em branco", "aneis[0].estagios[0].origemDeTransicoesProibidas[0].destino"),
                new Erro("Controlador", "não pode ficar em branco", "aneis[0].estagios[0].origemDeTransicoesProibidas[0].alternativo")
        ));

        estagio1AnelCom4Estagios.setOrigemDeTransicoesProibidas(null);

        transicaoProibida.setOrigem(estagio1AnelCom2Estagios);
        transicaoProibida.setDestino(estagio1AnelCom2Estagios);
        transicaoProibida.setAlternativo(estagio1AnelCom2Estagios);

        estagio1AnelCom2Estagios.setOrigemDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio1AnelCom2Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio1AnelCom2Estagios.setAlternativaDeTransicoesProibidas(Arrays.asList(transicaoProibida));


        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);

        assertEquals(3, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "O estágio de origem deve ser diferente do estágio de destino.", "aneis[1].estagios[0].origemDeTransicoesProibidas[0].origemEDestinoDiferentes"),
                new Erro("Controlador", "Esse estágio não pode ter um estágio de destino e alternativo ao mesmo tempo.", "aneis[1].estagios[0].aoMesmoTempoDestinoEAlternativo"),
                new Erro("Controlador", "O Estágio alternativo deve ser diferente da origem e do destino.", "aneis[1].estagios[0].origemDeTransicoesProibidas[0].estagioAlternativoDiferenteOrigemEDestino")
        ));

        estagio1AnelCom2Estagios.setDestinoDeTransicoesProibidas(null);
        estagio1AnelCom2Estagios.setAlternativaDeTransicoesProibidas(null);

        transicaoProibida.setOrigem(estagio1AnelCom2Estagios);
        transicaoProibida.setDestino(estagio1AnelCom4Estagios);
        transicaoProibida.setAlternativo(estagio2AnelCom2Estagios);

        estagio1AnelCom2Estagios.setOrigemDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio1AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio2AnelCom2Estagios.setAlternativaDeTransicoesProibidas(Arrays.asList(transicaoProibida));

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);

        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Esse estágio só pode ser proibido com estágios do mesmo anel.", "aneis[1].estagios[0].origemDeTransicoesProibidas[0].origemEDestinoPertencemAoMesmoAnel")
        ));


        estagio1AnelCom2Estagios.setOrigemDeTransicoesProibidas(null);
        estagio2AnelCom2Estagios.setAlternativaDeTransicoesProibidas(null);

        transicaoProibida.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibida.setDestino(estagio2AnelCom4Estagios);
        transicaoProibida.setAlternativo(estagio4AnelCom4Estagios);

        TransicaoProibida transicaoProibidaEstagio1ComEstagio3 = new TransicaoProibida();
        transicaoProibidaEstagio1ComEstagio3.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio3.setDestino(estagio3AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio3.setAlternativo(estagio4AnelCom4Estagios);

        TransicaoProibida transicaoProibidaEstagio1ComEstagio4 = new TransicaoProibida();
        transicaoProibidaEstagio1ComEstagio4.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio4.setDestino(estagio4AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio4.setAlternativo(estagio4AnelCom4Estagios);

        estagio1AnelCom4Estagios.setOrigemDeTransicoesProibidas(Arrays.asList(transicaoProibida, transicaoProibidaEstagio1ComEstagio3, transicaoProibidaEstagio1ComEstagio4));
        estagio2AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio3AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibidaEstagio1ComEstagio3));
        estagio4AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibidaEstagio1ComEstagio4));
        estagio4AnelCom4Estagios.setAlternativaDeTransicoesProibidas(Arrays.asList(transicaoProibida, transicaoProibidaEstagio1ComEstagio3, transicaoProibidaEstagio1ComEstagio4));

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class, ControladorTransicoesProibidasCheck.class);

        assertEquals(2, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
                new Erro("Controlador", "Esse estágio não pode ter um estágio de destino e alternativo ao mesmo tempo.", "aneis[0].estagios[3].aoMesmoTempoDestinoEAlternativo"),
                new Erro("Controlador", "O Estágio alternativo deve ser diferente da origem e do destino.", "aneis[0].estagios[0].origemDeTransicoesProibidas[2].estagioAlternativoDiferenteOrigemEDestino")
        ));

    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 4, controlador.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 3, controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().get(0);
        Estagio estagio2AnelCom4Estagios = anelCom4Estagios.getEstagios().get(1);
        Estagio estagio3AnelCom4Estagios = anelCom4Estagios.getEstagios().get(2);
        Estagio estagio4AnelCom4Estagios = anelCom4Estagios.getEstagios().get(3);

        assertEquals("Estagio 1 deve ter 2 origens.", 2, estagio1AnelCom4Estagios.getOrigemDeTransicoesProibidas().size());
        assertEquals("Estagio 2 deve ter 1 destino.", 1, estagio2AnelCom4Estagios.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 3 deve ter 1 destino.", 1, estagio3AnelCom4Estagios.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 4 deve ter 2 alternativos.", 2, estagio4AnelCom4Estagios.getAlternativaDeTransicoesProibidas().size());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();

        Controlador controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 4, controladorJson.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, controladorJson.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 3, controladorJson.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());

        Anel anelCom4EstagiosJson = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();


        Estagio estagio1AnelCom4EstagiosJson = anelCom4EstagiosJson.getEstagios().get(0);
        Estagio estagio2AnelCom4EstagiosJson = anelCom4EstagiosJson.getEstagios().get(1);
        Estagio estagio3AnelCom4EstagiosJson = anelCom4EstagiosJson.getEstagios().get(2);
        Estagio estagio4AnelCom4EstagiosJson = anelCom4EstagiosJson.getEstagios().get(3);

        assertEquals("Estagio 1 deve ter 2 origens.", 2, estagio1AnelCom4EstagiosJson.getOrigemDeTransicoesProibidas().size());
        assertEquals("Estagio 2 deve ter 1 destino.", 1, estagio2AnelCom4EstagiosJson.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 3 deve ter 1 destino.", 1, estagio3AnelCom4EstagiosJson.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 4 deve ter 2 alternativos.", 2, estagio4AnelCom4EstagiosJson.getAlternativaDeTransicoesProibidas().size());

    }


    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.transicoesProibidas().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorTransicoesProibidas();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.transicoesProibidas().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json, Controlador.class);

        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().get(0);
        Estagio estagio2AnelCom4Estagios = anelCom4Estagios.getEstagios().get(1);
        Estagio estagio3AnelCom4Estagios = anelCom4Estagios.getEstagios().get(2);
        Estagio estagio4AnelCom4Estagios = anelCom4Estagios.getEstagios().get(3);

        assertEquals("Estagio 1 deve ter 2 origens.", 2, estagio1AnelCom4Estagios.getOrigemDeTransicoesProibidas().size());
        assertEquals("Estagio 2 deve ter 1 destino.", 1, estagio2AnelCom4Estagios.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 3 deve ter 1 destino.", 1, estagio3AnelCom4Estagios.getDestinoDeTransicoesProibidas().size());
        assertEquals("Estagio 4 deve ter 2 alternativos.", 2, estagio4AnelCom4Estagios.getAlternativaDeTransicoesProibidas().size());
    }
}

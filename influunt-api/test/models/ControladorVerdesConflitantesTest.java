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
import static play.mvc.Http.Status.UNPROCESSABLE_ENTITY;
import static play.test.Helpers.route;

/**
 * Created by lesiopinheiro on 6/28/16.
 */
public class ControladorVerdesConflitantesTest extends ControladorTest {

    @Override
    @Test
    public void testVazio() {

        Controlador controlador = getControladorAssociacao();

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();
        anelAtivo.setAtivo(Boolean.TRUE);
        anelAtivo.setLatitude(1.0);
        anelAtivo.setLongitude(1.0);

        anelAtivo.setQuantidadeGrupoVeicular(2);
        anelAtivo.setEstagios(Arrays.asList(new Estagio(), new Estagio()));

        Estagio estagio1 = anelAtivo.getEstagios().get(0);
        Estagio estagio2 = anelAtivo.getEstagios().get(1);

        controlador.save();

        GrupoSemaforico grupoSemaforico3 = anelAtivo.getGruposSemaforicos().get(0);
        grupoSemaforico3.setTipo(TipoGrupoSemaforico.VEICULAR);
        GrupoSemaforico grupoSemaforico4 = anelAtivo.getGruposSemaforicos().get(1);
        grupoSemaforico4.setTipo(TipoGrupoSemaforico.VEICULAR);


        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico3);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico4);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        grupoSemaforico3.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico4.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        controlador.save();

        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);

        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "Esse grupo semafórico deve ter ao menos um verde conflitante", "aneis[0].gruposSemaforicos[0].aoMenosUmVerdeConflitante"),
                new Erro("Controlador", "Esse grupo semafórico deve ter ao menos um verde conflitante", "aneis[0].gruposSemaforicos[1].aoMenosUmVerdeConflitante"),
                new Erro("Controlador", "Esse grupo semafórico deve ter ao menos um verde conflitante", "aneis[1].gruposSemaforicos[0].aoMenosUmVerdeConflitante"),
                new Erro("Controlador", "Esse grupo semafórico deve ter ao menos um verde conflitante", "aneis[1].gruposSemaforicos[1].aoMenosUmVerdeConflitante")
        ));

        GrupoSemaforico grupoSemaforico1 = controlador.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforico2 = controlador.getGruposSemaforicos().get(1);

        grupoSemaforico1.addVerdeConflitante(grupoSemaforico1);
        grupoSemaforico2.addVerdeConflitante(grupoSemaforico2);
        grupoSemaforico3.addVerdeConflitante(grupoSemaforico3);
        grupoSemaforico4.addVerdeConflitante(grupoSemaforico4);


        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);

        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com ele mesmo", "aneis[0].gruposSemaforicos[0].naoConflitaComEleMesmo"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com ele mesmo", "aneis[0].gruposSemaforicos[1].naoConflitaComEleMesmo"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com ele mesmo", "aneis[1].gruposSemaforicos[0].naoConflitaComEleMesmo"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com ele mesmo", "aneis[1].gruposSemaforicos[1].naoConflitaComEleMesmo")
        ));

        grupoSemaforico1.setVerdesConflitantesOrigem(null);
        grupoSemaforico1.setVerdesConflitantesDestino(null);
        grupoSemaforico2.setVerdesConflitantesOrigem(null);
        grupoSemaforico2.setVerdesConflitantesDestino(null);
        grupoSemaforico3.setVerdesConflitantesOrigem(null);
        grupoSemaforico3.setVerdesConflitantesDestino(null);
        grupoSemaforico4.setVerdesConflitantesOrigem(null);
        grupoSemaforico4.setVerdesConflitantesDestino(null);
        grupoSemaforico1.addVerdeConflitante(grupoSemaforico3);
        grupoSemaforico3.addVerdeConflitante(grupoSemaforico1);
        grupoSemaforico4.addVerdeConflitante(grupoSemaforico2);
        grupoSemaforico2.addVerdeConflitante(grupoSemaforico4);


        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);
        assertEquals(4, erros.size());
        assertThat(erros, Matchers.hasItems(
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel", "aneis[0].gruposSemaforicos[0].naoConflitaComGruposDeOutroAnel"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel", "aneis[0].gruposSemaforicos[1].naoConflitaComGruposDeOutroAnel"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel", "aneis[1].gruposSemaforicos[0].naoConflitaComGruposDeOutroAnel"),
                new Erro("Controlador", "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel", "aneis[1].gruposSemaforicos[1].naoConflitaComGruposDeOutroAnel")
        ));

        grupoSemaforico1.setVerdesConflitantesOrigem(null);
        grupoSemaforico1.setVerdesConflitantesDestino(null);
        grupoSemaforico2.setVerdesConflitantesOrigem(null);
        grupoSemaforico2.setVerdesConflitantesDestino(null);
        grupoSemaforico3.setVerdesConflitantesOrigem(null);
        grupoSemaforico3.setVerdesConflitantesDestino(null);
        grupoSemaforico4.setVerdesConflitantesOrigem(null);
        grupoSemaforico4.setVerdesConflitantesDestino(null);
        grupoSemaforico1.addVerdeConflitante(grupoSemaforico2);
        grupoSemaforico2.addVerdeConflitante(grupoSemaforico1);
        grupoSemaforico3.addVerdeConflitante(grupoSemaforico4);
        grupoSemaforico4.addVerdeConflitante(grupoSemaforico3);


        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);

        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testNoValidationErro() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();
        List<Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class, ControladorVerdesConflitantesCheck.class);
        assertThat(erros, Matchers.empty());
    }

    @Override
    @Test
    public void testORM() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();
        assertNotNull(controlador.getId());
        assertEquals("Criação de aneis", 4, controlador.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controlador.getAneis().stream().filter(anel -> anel.isAtivo()).count());
        assertEquals("Criação de grupos semafóricos", 4, controlador.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 3, controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }

    @Override
    @Test
    public void testJSON() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        Controlador controladorJson = Json.fromJson(Json.toJson(controlador), Controlador.class);

        assertEquals(controlador.getId(), controladorJson.getId());
        assertControladorVerdesConflitantes(controlador, controladorJson);
        assertNotNull(controladorJson.getId());
        assertEquals("Criação de aneis", 4, controladorJson.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controladorJson.getAneis().stream().filter(anel -> anel.isAtivo()).count());

        Anel anelCom2Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorJson.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        assertEquals("Criação de grupos semafóricos Anel 2 Estagios", 2, anelCom2Estagios.getGruposSemaforicos().size());
        assertEquals("Criação de grupos semafóricos Anel 4 Estagios", 2, anelCom4Estagios.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 0, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 2, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());

    }

    private void assertControladorVerdesConflitantes(Controlador controlador, Controlador controladorJson) {
        GrupoSemaforico grupoSemaforico1 = controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G1")).findFirst().get();
        GrupoSemaforico grupoSemaforico2 = controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G2")).findFirst().get();
        GrupoSemaforico grupoSemaforico3 = controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G3")).findFirst().get();
        GrupoSemaforico grupoSemaforico4 = controlador.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G4")).findFirst().get();

        Anel anel1 = controladorJson.getAneis().stream().filter(anel -> anel.getPosicao().equals(1)).findFirst().get();
        Anel anel2 = controladorJson.getAneis().stream().filter(anel -> anel.getPosicao().equals(2)).findFirst().get();
        GrupoSemaforico grupoSemaforico1Json = anel1.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G1")).findFirst().get();
        GrupoSemaforico grupoSemaforico2Json = anel1.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G2")).findFirst().get();
        GrupoSemaforico grupoSemaforico3Json = anel2.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G3")).findFirst().get();
        GrupoSemaforico grupoSemaforico4Json = anel2.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.getDescricao().equals("G4")).findFirst().get();

        assertEquals(grupoSemaforico1, grupoSemaforico1Json);
        assertEquals(grupoSemaforico2, grupoSemaforico2Json);
        assertEquals(grupoSemaforico3, grupoSemaforico3Json);
        assertEquals(grupoSemaforico4, grupoSemaforico4Json);

        assertTrue("G1 contem G2", grupoSemaforico1.conflitaCom(grupoSemaforico2));
        assertTrue("G2 contem G1", grupoSemaforico2.conflitaCom(grupoSemaforico1));
        assertTrue("G3 contem G4", grupoSemaforico3.conflitaCom(grupoSemaforico4));
        assertTrue("G4 contem G3", grupoSemaforico4.conflitaCom(grupoSemaforico3));

        assertTrue("JSON - G1 contem G2", grupoSemaforico1Json.conflitaCom(grupoSemaforico2Json));
        assertTrue("JSON - G2 contem G1", grupoSemaforico2Json.conflitaCom(grupoSemaforico1Json));
        assertTrue("JSON - G3 contem G4", grupoSemaforico3Json.conflitaCom(grupoSemaforico4Json));
        assertTrue("JSON - G4 contem G3", grupoSemaforico4Json.conflitaCom(grupoSemaforico3Json));


    }

    @Override
    @Test
    public void testControllerValidacao() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.verdesConflitantes().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(UNPROCESSABLE_ENTITY, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        assertEquals(2, json.size());

    }

    @Override
    @Test
    public void testController() {
        Controlador controlador = getControladorVerdesConflitantes();

        Http.RequestBuilder postRequest = new Http.RequestBuilder().method("POST")
                .uri(routes.ControladoresController.verdesConflitantes().url()).bodyJson(Json.toJson(controlador));
        Result postResult = route(postRequest);

        assertEquals(OK, postResult.status());

        JsonNode json = Json.parse(Helpers.contentAsString(postResult));
        Controlador controladorRetornado = Json.fromJson(json, Controlador.class);

        assertControladorVerdesConflitantes(controlador, controladorRetornado);
        assertNotNull(controladorRetornado.getId());
        assertEquals("Criação de aneis", 4, controladorRetornado.getAneis().size());
        assertEquals("Total de aneis ativos", 2, controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo()).count());

        Anel anelCom2Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controladorRetornado.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        assertEquals("Criação de grupos semafóricos Anel 2 Estagios", 2, anelCom2Estagios.getGruposSemaforicos().size());
        assertEquals("Criação de grupos semafóricos Anel 4 Estagios", 2, anelCom4Estagios.getGruposSemaforicos().size());
        assertEquals("Total de grupos semaforicos de Pedestre", 0, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos de Pedestre", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isPedestre()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 2, anelCom2Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
        assertEquals("Total de grupos semaforicos Veiculares", 1, anelCom4Estagios.getGruposSemaforicos().stream().filter(grupoSemaforico -> grupoSemaforico.isVeicular()).count());
    }
}

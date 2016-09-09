package models;

import checks.Erro;
import com.google.inject.Singleton;
import config.WithInfluuntApplicationNoAuthentication;
import org.junit.Before;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/22/16.
 */
public abstract class ControladorTest extends WithInfluuntApplicationNoAuthentication {

    private static ControladorTestUtil controladorTestUtils;

    protected Usuario usuario;

    @Before
    public void setup() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        Area area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        Subarea subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Subarea 1");
        subarea.setNumero(1);
        subarea.save();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        ModeloControlador modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        usuario = new Usuario();
        usuario.setLogin("abc");
        usuario.setNome("Usuario ABC");
        usuario.setRoot(false);
        usuario.setEmail("abc@influunt.com.br");
        usuario.save();

        controladorTestUtils = new ControladorTestUtil(cidade, area, subarea, fabricante, modeloControlador);
    }

    protected Controlador getControlador() {
        return controladorTestUtils.getControlador();
    }

    protected Controlador getControladorDadosBasicos() {
        return controladorTestUtils.getControladorDadosBasicos();
    }

    protected Controlador getControladorAneis() {
        return controladorTestUtils.getControladorAneis();
    }

    protected Controlador getControladorGrupoSemaforicos() {
        return controladorTestUtils.getControladorGrupoSemaforicos();
    }

    protected Controlador getControladorAssociacao() {
        return controladorTestUtils.getControladorAssociacao();
    }

    protected Controlador getControladorVerdesConflitantes() {
        return controladorTestUtils.getControladorVerdesConflitantes();
    }

    protected Controlador getControladorTransicoesProibidas() {
        return controladorTestUtils.getControladorTransicoesProibidas();
    }

    protected Controlador getControladorAtrasoDeGrupo() {
        return controladorTestUtils.getControladorAtrasoDeGrupo();
    }

    protected Controlador getControladorTabelaDeEntreVerdes() {
        return controladorTestUtils.getControladorTabelaDeEntreVerdes();
    }

    protected Controlador getControladorAssociacaoDetectores() {
        return controladorTestUtils.getControladorAssociacaoDetectores();
    }

    protected Controlador getControladorPlanos() {
        return controladorTestUtils.getControladorPlanos();
    }

    protected Controlador getControladorTabelaHorario() {
        return controladorTestUtils.getControladorTabelaHorario();
    }


    // METODOS AUXILIARES

    protected void criarGrupoSemaforicoPlano(Anel anel, Plano plano) {
        controladorTestUtils.criarGrupoSemaforicoPlano(anel, plano);
    }

    protected void criarEstagioPlano(Anel anel, Plano plano, int posicoes[]) {
        controladorTestUtils.criarEstagioPlano(anel, plano, posicoes);
    }

    protected void criarGrupoSemaforico(Anel anel, TipoGrupoSemaforico tipo, Integer posicao) {
        controladorTestUtils.criarGrupoSemaforico(anel, tipo, posicao);
    }

    protected void criarDetector(Anel anel, TipoDetector tipo, Integer posicao, Boolean monitorado) {
        controladorTestUtils.criarDetector(anel, tipo, posicao, monitorado);
    }

    public abstract void testVazio();

    public abstract void testNoValidationErro();

    public abstract void testORM();

    public abstract void testJSON();

    public abstract void testControllerValidacao();

    public abstract void testController();

    public abstract List<Erro> getErros(Controlador controlador);

}

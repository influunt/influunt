package test.models;

import checks.Erro;
import models.*;
import org.junit.Before;
import status.StatusAtualControlador;
import test.config.WithInfluuntApplicationNoAuthentication;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.List;


/**
 * Created by rodrigosol on 6/22/16.
 */
public abstract class ControladorTest extends WithInfluuntApplicationNoAuthentication {

    private static ControladorTestUtil controladorTestUtils;

    protected Usuario usuario;

    @Before
    public void setup() {
        PlayJongo jongo = provideApp.injector().instanceOf(PlayJongo.class);
        StatusAtualControlador.jongo = jongo;

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

        controladorTestUtils = new ControladorTestUtil(area, subarea, fabricante, modeloControlador);
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

    protected void criarEstagioPlano(Anel anel, Plano plano, int posicoes[], int tempos[]) {
        controladorTestUtils.criarEstagioPlano(anel, plano, posicoes, tempos);
    }

    protected void criarGrupoSemaforico(Anel anel, TipoGrupoSemaforico tipo, Integer posicao) {
        controladorTestUtils.criarGrupoSemaforico(anel, tipo, posicao);
    }

    protected void criarDetector(Anel anel, TipoDetector tipo, Integer posicao, Boolean monitorado) {
        controladorTestUtils.criarDetector(anel, tipo, posicao, monitorado);
    }

    protected abstract void testVazio();

    protected abstract void testNoValidationErro();

    protected abstract void testORM();

    protected abstract void testJSON();

    protected abstract void testControllerValidacao();

    protected abstract void testController();

    protected abstract List<Erro> getErros(Controlador controlador);

}

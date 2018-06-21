package test.controllers;

import checks.Erro;
import models.*;
import org.junit.Before;
import status.StatusAtualControlador;
import status.StatusControladorFisico;
import test.config.WithInfluuntApplicationNoAuthentication;
import test.models.ControladorTestUtil;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.List;

/**
 * Created by lesiopinheiro on 8/31/16.
 */
public abstract class AbstractInfluuntControladorTest extends WithInfluuntApplicationNoAuthentication {

    protected static ControladorTestUtil controladorTestUtils;

    @Before
    public void setUpModels() {
        PlayJongo jongo = provideApp.injector().instanceOf(PlayJongo.class);
        StatusAtualControlador.jongo = jongo;
        StatusControladorFisico.jongo = jongo;

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

        controladorTestUtils = new ControladorTestUtil(area, subarea, fabricante, modeloControlador);
    }

    public abstract List<Erro> getErros(Controlador controlador);
}

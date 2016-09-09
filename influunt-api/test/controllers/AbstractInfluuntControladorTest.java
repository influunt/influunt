package controllers;

import checks.Erro;
import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.junit.Before;

import java.util.List;

/**
 * Created by lesiopinheiro on 8/31/16.
 */
public abstract class AbstractInfluuntControladorTest extends WithInfluuntApplicationNoAuthentication {

    protected static ControladorTestUtil controladorTestUtils;

    @Before
    public void setUpModels() {
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

        controladorTestUtils = new ControladorTestUtil(cidade, area, subarea, fabricante, modeloControlador);
    }

    public abstract List<Erro> getErros(Controlador controlador);
}

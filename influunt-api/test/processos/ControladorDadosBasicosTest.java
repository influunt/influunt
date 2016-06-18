package processos;


import checks.InfluuntValidator;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorDadosBasicosTest extends ControladorTest {


    @Test
    public void testSalvarNovoControladorVazio(){
        Controlador controlador = new Controlador();
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador);
        Assert.assertEquals(7,erros.size());
    }

    @Test
    public void testSalvarNovoControladorComDadosBasicos(){
        Controlador controlador = getControladorComDadosBasicos();
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador);
        controlador.save();
        Assert.assertEquals(0,erros.size());
        Assert.assertEquals("1.000.0001", controlador.getIdControlador());
        Controlador controlador2 = getControladorComDadosBasicos();
        controlador2.save();
        Assert.assertEquals("1.000.0002", controlador2.getIdControlador());
    }

}

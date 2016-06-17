package processos;


import checks.ControladorAneisCheck;
import checks.InfluuntValidator;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorAneisTest extends ControladorTest {


    @Test
    public void testSalvarUpdateControladorAneisVazio(){
        Controlador controlador = getControladorComDadosBasicos();
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(2, erros.size());
    }

    @Test
    public void testSalvarUpdateControladorTodosAneisInativos(){
        Controlador controlador = getControladorComDadosBasicos();
        Anel anel = new Anel();
        controlador.setAneis(Arrays.asList(anel,anel,anel,anel));
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(1, erros.size());

    }


    @Test
    public void testSalvarUpdateControladorComUmAnelAtivo(){
        Controlador controlador = getControladorComDadosBasicos();
        Anel anel = new Anel();
        Anel anelAtivo = new Anel();
        Anel anelAtivo2 = new Anel();

        anelAtivo.setAtivo(true);
        anelAtivo2.setAtivo(true);
        controlador.setAneis(Arrays.asList(anel,anel,anelAtivo,anelAtivo2));
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(10, erros.size());

        anelAtivo.setPosicao(1);
        anelAtivo.setLatitude(1.0);
        anelAtivo.setLongitude(2.0);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(7, erros.size());

        anelAtivo.setQuantidadeGrupoVeicular(9);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(6, erros.size());

        anelAtivo2.setQuantidadeGrupoPedestre(6);
        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(5, erros.size());
        anelAtivo2.setQuantidadeGrupoVeicular(9);
        anelAtivo.setQuantidadeGrupoPedestre(16);

        anelAtivo.setQuantidadeDetectorPedestre(100);
        anelAtivo2.setQuantidadeDetectorVeicular(100);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(8, erros.size());

        anelAtivo.setQuantidadeGrupoPedestre(0);
        anelAtivo2.setQuantidadeGrupoPedestre(0);

        anelAtivo.setQuantidadeGrupoVeicular(7);

        anelAtivo.setQuantidadeDetectorPedestre(1);
        anelAtivo2.setQuantidadeDetectorVeicular(1);

        anelAtivo2.setPosicao(1);
        anelAtivo2.setLatitude(1.0);
        anelAtivo2.setLongitude(2.0);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(2, erros.size());

        anelAtivo.setMovimentos(Arrays.asList(new Movimento(),new Movimento()));
        anelAtivo2.setMovimentos(Arrays.asList(new Movimento(),new Movimento()));

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(0, erros.size());
    }

    @Test
    public void testSalvarUpdateControladorComAnelValido(){
        Controlador controlador = getControladorComAneis();

        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(0, erros.size());

        Assert.assertEquals(10,controlador.getGruposSemaforicos().size());
        Assert.assertEquals(4,controlador.getDetectores().size());

    }


}

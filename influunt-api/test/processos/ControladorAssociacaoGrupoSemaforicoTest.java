package processos;


import checks.ControladorAneisCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.InfluuntValidator;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorAssociacaoGrupoSemaforicoTest extends ControladorTest {

    @Test
    public void testAssociaoVazia(){
        Controlador controlador = getControladorComAneis();

        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(6, erros.size());
    }

    @Test
    public void testAssociacaoDeUmEstagio(){
        Controlador controlador = getControladorComAneis();
        List<EstagioGrupoSemaforico> estagioGrupoSemaforicos = new ArrayList<EstagioGrupoSemaforico>();
        EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico();

        Estagio estagio = controlador.getAneis().get(3).getMovimentos().get(0).getEstagio();
        GrupoSemaforico grupoSemaforico = controlador.getAneis().get(3).getGruposSemaforicos().get(0);

        estagioGrupoSemaforico.setEstagio(estagio);
        estagioGrupoSemaforico.setGrupoSemaforico(grupoSemaforico);
        estagioGrupoSemaforicos.add(estagioGrupoSemaforico);

        estagio.setEstagiosGruposSemaforicos(estagioGrupoSemaforicos);
        grupoSemaforico.setEstagioGrupoSemaforicos(estagioGrupoSemaforicos);

        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(6, erros.size());

        grupoSemaforico.setTipo(TipoGrupoSemaforico.PEDESTRE);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(6, erros.size());

        grupoSemaforico.setTipo(TipoGrupoSemaforico.VEICULAR);

        erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(5, erros.size());
        Assert.assertEquals(2, controlador.getAneis().get(3).getEstagios().size());
    }

    @Test
    public void testAssociacaoOK(){
        Controlador controlador = getControladorComAssociacao();
        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador, Default.class, ControladorAneisCheck.class, ControladorAssociacaoGruposSemaforicosCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(0, erros.size());
    }
}

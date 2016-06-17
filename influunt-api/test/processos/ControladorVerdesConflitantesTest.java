package processos;


import checks.ControladorAneisCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.ControladorVerdesConflitantesCheck;
import checks.InfluuntValidator;
import models.Anel;
import models.Controlador;
import models.GrupoSemaforico;
import models.Movimento;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorVerdesConflitantesTest extends ControladorTest {


    @Test
    public void testSalvarUpdateControladorComAnelValido() {
        Controlador controlador = getControladorComAssociacao();

        List<InfluuntValidator.Erro> erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class,
                ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(4, erros.size());

        //Faz um grupo conflitar com ele mesmo
        GrupoSemaforico grupoSemaforico = controlador.getAneis().get(3).getGruposSemaforicos().get(0);
        grupoSemaforico.setVerdesConflitantes(Arrays.asList(grupoSemaforico));

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class,
                ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class);
        imprimeErros(erros);
        Assert.assertEquals(4, erros.size());



        GrupoSemaforico grupoSemaforicoAnelA = controlador.getAneis().get(3).getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoAnelB = controlador.getAneis().get(2).getGruposSemaforicos().get(0);
        grupoSemaforicoAnelA.setVerdesConflitantes(Arrays.asList(grupoSemaforicoAnelA,grupoSemaforicoAnelB));

        erros = new InfluuntValidator<Controlador>().validate(controlador,
                Default.class,
                ControladorAneisCheck.class,
                ControladorAssociacaoGruposSemaforicosCheck.class,
                ControladorVerdesConflitantesCheck.class);
        imprimeErros(erros);

        Assert.assertEquals(6, erros.size());



    }


}

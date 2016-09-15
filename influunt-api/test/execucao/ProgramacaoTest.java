package execucao;

import com.google.common.collect.Lists;
import models.GrupoSemaforicoPlano;
import models.Plano;
import org.junit.Test;
import os72c.client.v2.EstadoGrupoBaixoNivel;
import os72c.client.v2.Programacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ProgramacaoTest {

    @Test
    public void testIndex(){
        Plano p1 = new Plano();
        p1.setTempoCiclo(255);
        p1.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[] {new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano()}));

        Plano p2 = new Plano();
        p2.setTempoCiclo(254);
        p2.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[] {new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano()}));

        Plano p3 = new Plano();
        p3.setTempoCiclo(253);
        p3.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[] {new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano()}));

        Plano p4 = new Plano();
        p4.setTempoCiclo(252);
        p4.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[] {new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano(),new GrupoSemaforicoPlano()}));

        ArrayList<Plano> planos = new ArrayList<>(4);

        planos.add(p1);
        planos.add(p2);
        planos.add(p3);
        planos.add(p4);

        Programacao prog = new Programacao(planos);
        assertEquals(688246020,prog.getCicloMaximo());

        assertEquals(0,prog.getIndex(1,1));
        assertEquals(0,prog.getIndex(5,1));
        assertEquals(0,prog.getIndex(9,1));
        assertEquals(0,prog.getIndex(13,1));

        assertEquals(254,prog.getIndex(1,255));
        assertEquals(0,prog.getIndex(5,255));
        assertEquals(1,prog.getIndex(9,255));
        assertEquals(2,prog.getIndex(13,255));

        assertEquals(0,prog.getIndex(1,256));
        assertEquals(1,prog.getIndex(5,256));
        assertEquals(2,prog.getIndex(9,256));
        assertEquals(3,prog.getIndex(13,256));

        assertEquals(254,prog.getIndex(1,688246020));
        assertEquals(253,prog.getIndex(5,688246020));
        assertEquals(252,prog.getIndex(9,688246020));
        assertEquals(251,prog.getIndex(13,688246020));

    }
}

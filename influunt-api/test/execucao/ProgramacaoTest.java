package execucao;

import com.google.common.collect.Lists;
import models.*;
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

    private List<Plano> planosG1eG2;

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

        assertEquals(16,prog.getIndex(1,17));

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

    @Test
    public void testTrocaDePlanosCom1Grupo(){
        Programacao programacao = new Programacao(getPlanosG1());

        assertEquals(EstadoGrupoSemaforico.VERDE,programacao.getProgram(1).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE,programacao.getProgram(3).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO,programacao.getProgram(4).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO,programacao.getProgram(8).get(0));
        assertEquals(EstadoGrupoSemaforico.VERMELHO,programacao.getProgram(9).get(0));
        assertEquals(EstadoGrupoSemaforico.VERMELHO,programacao.getProgram(17).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE,programacao.getProgram(18).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE,programacao.getProgram(30).get(0));

        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(1));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(2));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(3));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(4));
        assertEquals(4,programacao.proximaJanelaParaTrocaDePlano(18));
        assertEquals(3,programacao.proximaJanelaParaTrocaDePlano(19));
        assertEquals(2,programacao.proximaJanelaParaTrocaDePlano(20));
        assertEquals(1,programacao.proximaJanelaParaTrocaDePlano(21));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(22));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(23));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(24));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(25));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(26));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(27));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(28));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(29));
        assertEquals(0,programacao.proximaJanelaParaTrocaDePlano(30));
    }

    @Test
    public void testTrocaDePlanosCom2Grupo() {
        Programacao programacao = new Programacao(getPlanosG1eG2());

        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(1).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(3).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(4).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(8).get(0));
        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(9).get(0));
        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(17).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(18).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(30).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(31).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(32).get(0));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(33).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(34).get(0));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(35).get(0));


        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(1).get(1));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(12).get(1));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(13).get(1));
        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(17).get(1));
        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(18).get(1));
        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(26).get(1));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(27).get(1));
        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(35).get(1));

        assertEquals(1, programacao.proximaJanelaParaTrocaDePlano(1));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(2));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(3));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(4));
        assertEquals(4, programacao.proximaJanelaParaTrocaDePlano(18));
        assertEquals(3, programacao.proximaJanelaParaTrocaDePlano(19));
        assertEquals(2, programacao.proximaJanelaParaTrocaDePlano(20));
        assertEquals(1, programacao.proximaJanelaParaTrocaDePlano(21));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(22));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(23));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(24));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(25));
        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(26));
        assertEquals(10, programacao.proximaJanelaParaTrocaDePlano(27));
        assertEquals(9, programacao.proximaJanelaParaTrocaDePlano(28));
        assertEquals(8, programacao.proximaJanelaParaTrocaDePlano(29));
        assertEquals(7, programacao.proximaJanelaParaTrocaDePlano(30));
        assertEquals(6, programacao.proximaJanelaParaTrocaDePlano(31));
        assertEquals(5, programacao.proximaJanelaParaTrocaDePlano(32));
        assertEquals(4, programacao.proximaJanelaParaTrocaDePlano(33));
        assertEquals(3, programacao.proximaJanelaParaTrocaDePlano(34));
        assertEquals(2, programacao.proximaJanelaParaTrocaDePlano(35));

    }

    @Test
    public void testMudancaGrupo() {
        Programacao programacao = new Programacao(getPlanosG1eG2());
        long mudancas = programacao.getCicloMaximo();
        for(int i = 1; i <= programacao.getCicloMaximo(); i++){
            if(programacao.novaConfiguracaoSeHouverMudanca(i,i+1) == null){
                mudancas--;
            }
        }

        assertEquals(37,mudancas);

    }


        private List<Plano> getPlanosG1() {
        ArrayList<Plano> planos = new ArrayList<>();
        GrupoSemaforico g1 = new GrupoSemaforico();
        g1.setTempoVerdeSeguranca(5);
        Intervalo g1i1 = new Intervalo();
        g1i1.setOrdem(0);
        g1i1.setTamanho(3);
        g1i1.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
        Intervalo g1i2 = new Intervalo();
        g1i2.setOrdem(1);
        g1i2.setTamanho(5);
        g1i2.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.AMARELO);

        Intervalo g1i3 = new Intervalo();
        g1i3.setOrdem(2);
        g1i3.setTamanho(9);
        g1i3.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERMELHO);


        Intervalo g1i4 = new Intervalo();
        g1i4.setOrdem(3);
        g1i4.setTamanho(13);
        g1i4.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);

        List<Intervalo> intervalosG1 = new ArrayList<>();
        intervalosG1.add(g1i1);
        intervalosG1.add(g1i2);
        intervalosG1.add(g1i3);
        intervalosG1.add(g1i4);

        GrupoSemaforicoPlano g1p1 = new GrupoSemaforicoPlano();
        g1p1.setGrupoSemaforico(g1);
        g1p1.setIntervalos(intervalosG1);

        Plano p1 = new Plano();
        p1.setTempoCiclo(30);
        ArrayList<GrupoSemaforicoPlano> grupoSemaforicoPlanos = new ArrayList<>();
        grupoSemaforicoPlanos.add(g1p1);
        p1.setGruposSemaforicosPlanos(grupoSemaforicoPlanos);
        planos.add(p1);

        return planos;
    }


    public List<Plano> getPlanosG1eG2() {
        List<Plano> planos = getPlanosG1();

        GrupoSemaforico g1 = new GrupoSemaforico();
        g1.setTempoVerdeSeguranca(11);
        Intervalo g1i1 = new Intervalo();
        g1i1.setOrdem(0);
        g1i1.setTamanho(12);
        g1i1.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
        Intervalo g1i2 = new Intervalo();
        g1i2.setOrdem(1);
        g1i2.setTamanho(5);
        g1i2.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.AMARELO);

        Intervalo g1i3 = new Intervalo();
        g1i3.setOrdem(2);
        g1i3.setTamanho(9);
        g1i3.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERMELHO);


        Intervalo g1i4 = new Intervalo();
        g1i4.setOrdem(3);
        g1i4.setTamanho(9);
        g1i4.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);

        List<Intervalo> intervalosG1 = new ArrayList<>();
        intervalosG1.add(g1i1);
        intervalosG1.add(g1i2);
        intervalosG1.add(g1i3);
        intervalosG1.add(g1i4);

        GrupoSemaforicoPlano g1p1 = new GrupoSemaforicoPlano();
        g1p1.setGrupoSemaforico(g1);
        g1p1.setIntervalos(intervalosG1);

        Plano p1 = new Plano();
        p1.setTempoCiclo(35);
        ArrayList<GrupoSemaforicoPlano> grupoSemaforicoPlanos = new ArrayList<>();
        grupoSemaforicoPlanos.add(g1p1);
        p1.setGruposSemaforicosPlanos(grupoSemaforicoPlanos);
        planos.add(p1);

        return planos;
    }
}

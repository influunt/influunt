//package execucao;
//
//import config.WithInfluuntApplicationNoAuthentication;
//import engine.GerenciadorDeIntervalos;
//import integracao.ControladorHelper;
//import models.*;
//import org.junit.Test;
//import os72c.client.v2.ResultadoSimulacao;
//import os72c.client.v2.SimulacaoBuilder;
//import os72c.client.v2.Simulador;
//import os72c.client.v2.VelocidadeSimulacao;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//
///**
// * Created by rodrigosol on 9/8/16.
// */
//public class ProgramacaoTest extends WithInfluuntApplicationNoAuthentication {
//
//    private List<Plano> planosG1eG2;
//
//    @Test
//    public void testIndex() {
//        Anel anel = new Anel();
//        anel.setPosicao(1);
//        VersaoPlano versaoPlano = new VersaoPlano();
//        versaoPlano.setAnel(anel);
//
//        GrupoSemaforico g = new GrupoSemaforico();
//        g.setTempoVerdeSeguranca(1);
//        GrupoSemaforicoPlano gsf = new GrupoSemaforicoPlano();
//        gsf.setGrupoSemaforico(g);
//        Plano p1 = new Plano();
//        p1.setTempoCiclo(255);
//        p1.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[]{gsf, gsf, gsf, gsf}));
//        p1.setVersaoPlano(versaoPlano);
//
//        Plano p2 = new Plano();
//        p2.setTempoCiclo(254);
//        p2.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[]{gsf, gsf, gsf, gsf}));
//        p2.setVersaoPlano(versaoPlano);
//
//        Plano p3 = new Plano();
//        p3.setTempoCiclo(253);
//        p3.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[]{gsf, gsf, gsf, gsf}));
//        p3.setVersaoPlano(versaoPlano);
//
//        Plano p4 = new Plano();
//        p4.setTempoCiclo(252);
//        p4.setGruposSemaforicosPlanos(Arrays.asList(new GrupoSemaforicoPlano[]{gsf, gsf, gsf, gsf}));
//        p4.setVersaoPlano(versaoPlano);
//
//        ArrayList<Plano> planos = new ArrayList<>(4);
//
//        planos.add(p1);
//        planos.add(p2);
//        planos.add(p3);
//        planos.add(p4);
//
//        GerenciadorDeIntervalos prog = new GerenciadorDeIntervalos(planos);
//        assertEquals(688246020, prog.getCicloMaximo());
//
//        assertEquals(0, prog.getIndex(1, 1));
//        assertEquals(0, prog.getIndex(5, 1));
//        assertEquals(0, prog.getIndex(9, 1));
//        assertEquals(0, prog.getIndex(13, 1));
//
//        assertEquals(16, prog.getIndex(1, 17));
//
//        assertEquals(254, prog.getIndex(1, 255));
//        assertEquals(0, prog.getIndex(5, 255));
//        assertEquals(1, prog.getIndex(9, 255));
//        assertEquals(2, prog.getIndex(13, 255));
//
//        assertEquals(0, prog.getIndex(1, 256));
//        assertEquals(1, prog.getIndex(5, 256));
//        assertEquals(2, prog.getIndex(9, 256));
//        assertEquals(3, prog.getIndex(13, 256));
//
//        assertEquals(254, prog.getIndex(1, 688246020));
//        assertEquals(253, prog.getIndex(5, 688246020));
//        assertEquals(252, prog.getIndex(9, 688246020));
//        assertEquals(251, prog.getIndex(13, 688246020));
//
//    }
//
//    @Test
//    public void testTrocaDePlanosCom1Grupo() {
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(getPlanosG1());
//
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(1).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(3).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(4).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(8).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(9).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(17).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(18).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(30).get(0));
//
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(1));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(2));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(3));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(4));
//        assertEquals(4, programacao.proximaJanelaParaTrocaDePlano(18));
//        assertEquals(3, programacao.proximaJanelaParaTrocaDePlano(19));
//        assertEquals(2, programacao.proximaJanelaParaTrocaDePlano(20));
//        assertEquals(1, programacao.proximaJanelaParaTrocaDePlano(21));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(22));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(23));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(24));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(25));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(26));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(27));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(28));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(29));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(30));
//    }
//
//    @Test
//    public void testTrocaDePlanosCom2Grupo() {
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(getPlanosG1eG2());
//
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(1).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(3).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(4).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(8).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(9).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(17).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(18).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(30).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(31).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(32).get(0));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(33).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(34).get(0));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(35).get(0));
//
//
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(1).get(1));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(12).get(1));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(13).get(1));
//        assertEquals(EstadoGrupoSemaforico.AMARELO, programacao.getProgram(17).get(1));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(18).get(1));
//        assertEquals(EstadoGrupoSemaforico.VERMELHO, programacao.getProgram(26).get(1));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(27).get(1));
//        assertEquals(EstadoGrupoSemaforico.VERDE, programacao.getProgram(35).get(1));
//
//        assertEquals(1, programacao.proximaJanelaParaTrocaDePlano(1));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(2));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(3));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(4));
//        assertEquals(4, programacao.proximaJanelaParaTrocaDePlano(18));
//        assertEquals(3, programacao.proximaJanelaParaTrocaDePlano(19));
//        assertEquals(2, programacao.proximaJanelaParaTrocaDePlano(20));
//        assertEquals(1, programacao.proximaJanelaParaTrocaDePlano(21));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(22));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(23));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(24));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(25));
//        assertEquals(0, programacao.proximaJanelaParaTrocaDePlano(26));
//        assertEquals(10, programacao.proximaJanelaParaTrocaDePlano(27));
//        assertEquals(9, programacao.proximaJanelaParaTrocaDePlano(28));
//        assertEquals(8, programacao.proximaJanelaParaTrocaDePlano(29));
//        assertEquals(7, programacao.proximaJanelaParaTrocaDePlano(30));
//        assertEquals(6, programacao.proximaJanelaParaTrocaDePlano(31));
//        assertEquals(5, programacao.proximaJanelaParaTrocaDePlano(32));
//        assertEquals(4, programacao.proximaJanelaParaTrocaDePlano(33));
//        assertEquals(3, programacao.proximaJanelaParaTrocaDePlano(34));
//        assertEquals(2, programacao.proximaJanelaParaTrocaDePlano(35));
//
//    }
//
//    @Test
//    public void testMudancaGrupo() {
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(getPlanosG1eG2());
//        long mudancas = programacao.getCicloMaximo();
//        for (int i = 1; i <= programacao.getCicloMaximo(); i++) {
//            if (programacao.novaConfiguracaoSeHouverMudanca(i, i + 1) == null) {
//                mudancas--;
//            }
//        }
//
//        assertEquals(37, mudancas);
//    }
//
//    @Test
//    public void testIndexEstagios() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(planos);
//
//        assertEquals(0, programacao.getIndexAnel(1, 1));
//        assertEquals(0, programacao.getIndexAnel(2, 1));
//        assertEquals(2, programacao.getIndexAnel(1, 55));
//        assertEquals(54, programacao.getIndexAnel(2, 55));
//        assertEquals(7, programacao.getIndexAnel(1, 60));
//        assertEquals(3, programacao.getIndexAnel(2, 60));
//    }
//
//    @Test
//    public void testSimulacao() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//
//        Simulador simulador = new SimulacaoBuilder().dataInicio(0)
//                .inicioSimulacao(0)
//                .fimSimulacao(728)
//                .velocidadeSimulacao(VelocidadeSimulacao.RESULTADO_FINAL)
//                .planos(planos)
//                .build();
//
//        ResultadoSimulacao resultado = simulador.simular();
//        assertEquals(729, resultado.getTempoSimulacao());
//
//        resultado.tempoGrupos().forEach(i -> {
//            i.values().forEach((h) -> {
//                assertEquals(729, h.values().stream().mapToInt(Long::intValue).sum());
//            });
//        });
//
////        assertEquals(477l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.VERMELHO).longValue());
////        assertEquals(168l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.VERDE).longValue());
////        assertEquals(42l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.AMARELO).longValue());
////        assertEquals(42l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.VERMELHO_LIMPEZA).longValue());
////
////        assertEquals(495l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.VERMELHO).longValue());
////        assertEquals(130l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.VERDE).longValue());
////        assertEquals(39l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.AMARELO).longValue());
////        assertEquals(65l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.VERMELHO_LIMPEZA).longValue());
//
//    }
//
//    @Test
//    public void testSimulacao2() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//
//        Simulador simulador = new SimulacaoBuilder().dataInicio(0)
//                .inicioSimulacao(727)
//                .fimSimulacao(729)
//                .velocidadeSimulacao(VelocidadeSimulacao.TEMPO_REAL)
//                .planos(planos)
//                .build();
//
//        ResultadoSimulacao resultado = simulador.simular();
//        assertEquals(3, resultado.getTempoSimulacao());
//
////        resultado.tempoGrupos().forEach(i -> {
////            i.values().forEach((h) -> {
////                assertEquals(3, h.values().stream().mapToInt(Long::intValue).sum());
////            });
////        });
//
////        assertEquals(3l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.VERMELHO).longValue());
////        assertEquals(3l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.VERMELHO).longValue());
//
//    }
//
//    @Test
//    public void testSimulacao3() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//
//        Simulador simulador = new SimulacaoBuilder().dataInicio(7)
//                .inicioSimulacao(7)
//                .fimSimulacao(7)
//                .velocidadeSimulacao(VelocidadeSimulacao.TEMPO_REAL)
//                .planos(planos)
//                .build();
//
//        ResultadoSimulacao resultado = simulador.simular();
//        assertEquals(1, resultado.getTempoSimulacao());
//
//        resultado.tempoGrupos().forEach(i -> {
//            i.values().forEach((h) -> {
//                assertEquals(1, h.values().stream().mapToInt(Long::intValue).sum());
//            });
//        });
//
//        assertEquals(1l, resultado.getTemposGrupo(0, 1).get(EstadoGrupoSemaforico.VERMELHO).longValue());
//        assertEquals(1l, resultado.getTemposGrupo(0, 6).get(EstadoGrupoSemaforico.VERMELHO).longValue());
//
//    }
//
//    @Test
//    public void testTrocaDeEstagios() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(planos);
//
//        assertEquals(1, programacao.getEstagiosAtuais(1).get(0).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(18).get(0).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(19).get(0).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(34).get(0).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(35).get(0).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(52).get(0).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(53).get(0).getPosicao().longValue());
//
//        assertEquals(1, programacao.getEstagiosAtuais(1).get(1).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(18).get(1).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(19).get(1).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(34).get(1).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(35).get(1).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(52).get(1).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(53).get(1).getPosicao().longValue());
//
//        assertEquals(1, programacao.getEstagiosAtuais(1).get(0).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(18).get(0).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(19).get(0).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(38).get(0).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(39).get(0).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(56).get(0).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(57).get(0).getPosicao().longValue());
//
//        assertEquals(1, programacao.getEstagiosAtuais(1).get(1).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(18).get(1).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(19).get(1).getPosicao().longValue());
//        assertEquals(3, programacao.getEstagiosAtuais(38).get(1).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(39).get(1).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(56).get(1).getPosicao().longValue());
//        assertEquals(1, programacao.getEstagiosAtuais(57).get(1).getPosicao().longValue());
//
//        assertEquals(2, programacao.getEstagiosAtuais(384).get(0).getPosicao().longValue());
//        assertEquals(2, programacao.getEstagiosAtuais(384).get(1).getPosicao().longValue());
//    }
//
//    @Test
//    public void testMudancaEstagio() {
//        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
//
//        List<Plano> planos = new ArrayList<>();
//        planos.add(getPlano(controlador, 1, 1));
//        planos.add(getPlano(controlador, 2, 1));
//        GerenciadorDeIntervalos programacao = new GerenciadorDeIntervalos(planos);
//
//        long mudancas = programacao.getCicloMaximo();
//        for (int i = 1; i <= programacao.getCicloMaximo(); i++) {
//            if (programacao.novaConfiguracaoEstagioSeHouverMudanca(i, i + 1) == null) {
//                mudancas--;
//            }
//        }
//
//        assertEquals(76, mudancas);
//    }
//
//    private Plano getPlano(Controlador controlador, Integer posicaoAnel, Integer posicaoPlano) {
//        return controlador
//                .getAneis()
//                .stream()
//                .filter(anel -> anel.getPosicao().equals(posicaoAnel))
//                .findFirst()
//                .get()
//                .getPlanos()
//                .stream()
//                .filter(plano1 -> plano1.getPosicao().equals(posicaoPlano))
//                .findFirst()
//                .get();
//    }
//
//    private List<Plano> getPlanosG1() {
//        Anel anel = new Anel();
//        anel.setPosicao(1);
//        VersaoPlano versaoPlano = new VersaoPlano();
//        versaoPlano.setAnel(anel);
//
//        ArrayList<Plano> planos = new ArrayList<>();
//        GrupoSemaforico g1 = new GrupoSemaforico();
//        g1.setTempoVerdeSeguranca(5);
//        Intervalo g1i1 = new Intervalo();
//        g1i1.setOrdem(0);
//        g1i1.setTamanho(3);
//        g1i1.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
//        Intervalo g1i2 = new Intervalo();
//        g1i2.setOrdem(1);
//        g1i2.setTamanho(5);
//        g1i2.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.AMARELO);
//
//        Intervalo g1i3 = new Intervalo();
//        g1i3.setOrdem(2);
//        g1i3.setTamanho(9);
//        g1i3.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERMELHO);
//
//
//        Intervalo g1i4 = new Intervalo();
//        g1i4.setOrdem(3);
//        g1i4.setTamanho(13);
//        g1i4.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
//
//        List<Intervalo> intervalosG1 = new ArrayList<>();
//        intervalosG1.add(g1i1);
//        intervalosG1.add(g1i2);
//        intervalosG1.add(g1i3);
//        intervalosG1.add(g1i4);
//
//        GrupoSemaforicoPlano g1p1 = new GrupoSemaforicoPlano();
//        g1p1.setGrupoSemaforico(g1);
//        g1p1.setIntervalos(intervalosG1);
//
//        Plano p1 = new Plano();
//        p1.setTempoCiclo(30);
//        ArrayList<GrupoSemaforicoPlano> grupoSemaforicoPlanos = new ArrayList<>();
//        grupoSemaforicoPlanos.add(g1p1);
//        p1.setGruposSemaforicosPlanos(grupoSemaforicoPlanos);
//        p1.setVersaoPlano(versaoPlano);
//        planos.add(p1);
//
//        return planos;
//    }
//
//    public List<Plano> getPlanosG1eG2() {
//        Anel anel = new Anel();
//        anel.setPosicao(1);
//        VersaoPlano versaoPlano = new VersaoPlano();
//        versaoPlano.setAnel(anel);
//
//        List<Plano> planos = getPlanosG1();
//
//        GrupoSemaforico g1 = new GrupoSemaforico();
//        g1.setTempoVerdeSeguranca(11);
//        Intervalo g1i1 = new Intervalo();
//        g1i1.setOrdem(0);
//        g1i1.setTamanho(12);
//        g1i1.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
//        Intervalo g1i2 = new Intervalo();
//        g1i2.setOrdem(1);
//        g1i2.setTamanho(5);
//        g1i2.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.AMARELO);
//
//        Intervalo g1i3 = new Intervalo();
//        g1i3.setOrdem(2);
//        g1i3.setTamanho(9);
//        g1i3.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERMELHO);
//
//
//        Intervalo g1i4 = new Intervalo();
//        g1i4.setOrdem(3);
//        g1i4.setTamanho(9);
//        g1i4.setEstadoGrupoSemaforico(EstadoGrupoSemaforico.VERDE);
//
//        List<Intervalo> intervalosG1 = new ArrayList<>();
//        intervalosG1.add(g1i1);
//        intervalosG1.add(g1i2);
//        intervalosG1.add(g1i3);
//        intervalosG1.add(g1i4);
//
//        GrupoSemaforicoPlano g1p1 = new GrupoSemaforicoPlano();
//        g1p1.setGrupoSemaforico(g1);
//        g1p1.setIntervalos(intervalosG1);
//
//        Plano p1 = new Plano();
//        p1.setTempoCiclo(35);
//        ArrayList<GrupoSemaforicoPlano> grupoSemaforicoPlanos = new ArrayList<>();
//        grupoSemaforicoPlanos.add(g1p1);
//        p1.setGruposSemaforicosPlanos(grupoSemaforicoPlanos);
//        p1.setVersaoPlano(versaoPlano);
//        planos.add(p1);
//
//        return planos;
//    }
//
//
//}

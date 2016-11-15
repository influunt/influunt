package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import engine.AgendamentoTrocaPlano;
import engine.IntervaloGrupoSemaforico;
import engine.Motor;
import engine.MotorCallback;
import integracao.ControladorHelper;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest extends WithInfluuntApplicationNoAuthentication implements MotorCallback {

    private Controlador controlador;
    private DateTime inicioExecucao;
    private DateTime inicioControlador;
    private HashMap<DateTime, Evento> listaTrocaPlano;
    private HashMap<DateTime, HashMap<Integer, Evento>> listaTrocaPlanoEfetiva;
    private HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaEstagios;
    private HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaHistoricoEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }


    @Test
    public void motorTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlano.get(inicioExecucao.plusSeconds(60)).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 1, listaTrocaPlano.get(inicioExecucao.plusSeconds(120)).getPosicaoPlano().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(34)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(52)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(70)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(86)).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(104)).get(1).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(104)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(114)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(134)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(154)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(172)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(190)).get(1).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(206)).get(1).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(18)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(39)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(59)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(77)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(98)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(118)).get(2).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(118)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(136)).get(2).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(158)).get(2).getEstagio().getPosicao().intValue());

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(21)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(38)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 4, listaEstagios.get(inicioExecucao.plusSeconds(56)).get(3).getEstagio().getPosicao().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaModoApagadoTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 19, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 19, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 6, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 8000, 18000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 0, 5000, EstadoGrupoSemaforico.DESLIGADO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 0, 5000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 5000, 8000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 8000, 18000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(138)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void saidaModoIntermitenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 18, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 18, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        //Avancar
        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(75)).get(3).getPosicaoPlano().intValue());
        assertNull("Estagio atual", listaEstagios.get(inicioExecucao.plusSeconds(75)).get(3).getEstagio().getPosicao());
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 11, 14000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 12, 14000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 14, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 0, 6000, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 6000, 11000, EstadoGrupoSemaforico.VERMELHO_LIMPEZA));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 15, 14000, 255000, EstadoGrupoSemaforico.DESLIGADO));

        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 0, 11000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 11000, 14000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(75, new GrupoCheck(3, 16, 14000, 255000, EstadoGrupoSemaforico.AMARELO_INTERMITENTE));

        assertEquals("Plano Atual", 1, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(120)).get(3).getPosicaoPlano().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(120)).get(3).getEstagio().getPosicao().intValue());
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 11, 3000, 13000, EstadoGrupoSemaforico.VERDE));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 12, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 13, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 14, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 15, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 0, 3000, EstadoGrupoSemaforico.VERMELHO));
        verificaGruposSemaforicos(120, new GrupoCheck(3, 16, 3000, 13000, EstadoGrupoSemaforico.VERMELHO));

        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(133)).get(3).getEstagio().getPosicao().intValue());
    }

    @Test
    public void entradaModoIntermitenteTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 20, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 20, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(80)).get(2).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(3).getPosicaoPlano().intValue());
    }

    @Test
    public void entradaESaidaModoIntermitenteNoEntreverdeTest() throws IOException {
        inicioControlador = new DateTime(2016, 10, 20, 21, 0, 0);
        inicioExecucao = new DateTime(2016, 10, 20, 21, 0, 0);
        Motor motor = new Motor(controlador, inicioControlador, inicioExecucao, this);

        avancarSegundos(motor, 210);

        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(80)).get(2).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 16, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(58)).get(3).getPosicaoPlano().intValue());

        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(69)).get(1).getPosicaoPlano().intValue());
        assertEquals("Plano Atual", 10, listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(60)).get(3).getPosicaoPlano().intValue());
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        listaTrocaPlano.put(timestamp, eventoAtual);
    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        if (!listaTrocaPlanoEfetiva.containsKey(agendamentoTrocaPlano.getMomentoDaTroca())) {
            listaTrocaPlanoEfetiva.put(agendamentoTrocaPlano.getMomentoDaTroca(), new HashMap<>());
        }
        listaTrocaPlanoEfetiva.get(agendamentoTrocaPlano.getMomentoDaTroca()).put(agendamentoTrocaPlano.getAnel(), agendamentoTrocaPlano.getEvento());
    }


    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!listaEstagios.containsKey(timestamp)) {
            listaEstagios.put(timestamp, new HashMap<>());
        }
        listaEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!listaHistoricoEstagios.containsKey(timestamp)) {
            listaHistoricoEstagios.put(timestamp, new HashMap<>());
        }
        listaHistoricoEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }


    private void avancarSegundos(Motor motor, long i) {
        long quantidade = i * 10L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    private void avancarMilis(Motor motor, long i) {
        long quantidade = i;
        while (quantidade-- > 0) {
            motor.tick();
        }
    }

    private void avancarHoras(Motor motor, long i) {
        long quantidade = i * 36000L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    private void avancarMinutos(Motor motor, long i) {
        long quantidade = i * 600L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    private void avancarDias(Motor motor, long i) {
        long quantidade = i * 864000L;
        while ((quantidade--) > 0) {
            motor.tick();
        }
    }

    private void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    private void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    private void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    public class GrupoCheck {

        private final int anel;

        private final int grupo;

        private final long inicio;

        private final long fim;

        private final EstadoGrupoSemaforico estado;

        public GrupoCheck(int anel, int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.anel = anel;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public void check(HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).get(anel).getEstados().get(this.grupo).get(this.inicio));
        }
    }

}

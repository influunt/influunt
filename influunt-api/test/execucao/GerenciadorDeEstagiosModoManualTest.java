package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.*;
import engine.TipoEvento;
import integracao.ControladorHelper;
import models.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosModoManualTest extends WithInfluuntApplicationNoAuthentication {

    private DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    private DateTime inicioExecucao = inicioControlador;

    private Controlador controlador;

    private HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios;

    private HashMap<DateTime, IntervaloGrupoSemaforico> listaHistoricoEstagios;

    private GerenciadorDeEstagios gerenciadorDeEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Test
    public void repeticaoDeEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual();
        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 5);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 15);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 200);

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(120)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(138)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void limitePermanenciaEstagio() {
        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual();
        avancar(gerenciadorDeEstagios, 100);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 200);

        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(18)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(36)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(58)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(110)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(176).plus(100)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(198)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(216)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(234)).getEstagio().getPosicao().intValue());
    }

    @Test
    public void repeticaoDeEstagioComDemandaPrioritariaComExecucao() {
        Anel anel = getAnel(2);
        Plano plano = getPlanoDemandaPrioritaria(anel);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(2, plano);
        Detector detector = getDetector(anel, 1);

        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 10);
        acionarModoManual();
        avancar(gerenciadorDeEstagios, 90);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 5);
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR, detector));
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 55);
        trocarEstagioModoManual();
        avancar(gerenciadorDeEstagios, 200);

        imprimirListaEstagios(listaEstagios);
        plano.imprimirTabelaEntreVerde();

        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(28)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(50)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(100)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 3, listaEstagios.get(inicioExecucao.plusSeconds(117)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 1, listaEstagios.get(inicioExecucao.plusSeconds(156)).getEstagio().getPosicao().intValue());
        assertEquals("Estagio atual", 2, listaEstagios.get(inicioExecucao.plusSeconds(160)).getEstagio().getPosicao().intValue());
    }

    private void acionarModoManual() {
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_MODO_MANUAL));
    }

    private void trocarEstagioModoManual() {
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.TROCA_ESTAGIO_MANUAL));
    }

    private void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    private void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios,inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    private void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    @NotNull
    private GerenciadorDeEstagios getGerenciadorDeEstagios(int anel, Plano plano) {
        return new GerenciadorDeEstagios(anel, inicioControlador, inicioExecucao, plano, new GerenciadorDeEstagiosCallback() {

            @Override
            public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
                listaEstagios.put(timestamp, intervalos);
            }

            @Override
            public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
                listaHistoricoEstagios.put(timestamp, intervalos);
            }

            @Override
            public void onCicloEnds(int anel, Long numeroCiclos) {

            }

            @Override
            public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

            }

        });
    }

    private Plano getPlano(Anel anel, int posicao) {
        return anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    private Plano getPlanoDemandaPrioritaria(Anel anel) {
        Plano plano = new ControladorHelper().criarPlano(anel, 13, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 32);

        plano.setEstagiosPlanos(null);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(1);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(1));
        estagioPlano.setTempoVerde(20);
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(2);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(2));
        estagioPlano.setTempoVerde(15);
        plano.addEstagios(estagioPlano);

        Detector detector = anel.getDetectores().get(0);
        detector.setTipo(TipoDetector.VEICULAR);
        detector.setPosicao(1);
        detector.getEstagio().setDemandaPrioritaria(true);
        detector.getEstagio().setTempoVerdeDemandaPrioritaria(30);
        return plano;
    }

    private Plano getPlanoDemandaPrioritariaEDispensavel(Anel anel) {
        Plano plano = getPlanoDemandaPrioritaria(anel);
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(3);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(1));
        estagioPlano.setTempoVerde(10);
        estagioPlano.setDispensavel(true);
        plano.addEstagios(estagioPlano);

        estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(4);
        estagioPlano.setPlano(plano);
        estagioPlano.setEstagio(anel.findEstagioByPosicao(2));
        estagioPlano.setTempoVerde(18);
        plano.addEstagios(estagioPlano);

        return plano;
    }

    private Anel getAnel(int posicao) {
        return controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    private Detector getDetector(Anel anel, int posicao) {
        return anel.getDetectores().stream().filter(det -> det.getPosicao().equals(posicao)).findFirst().get();
    }

    private EstagioPlano getEstagioPlano(Plano plano, int posicao) {
        return plano.getEstagiosPlanos().stream().filter(ep -> ep.getEstagio().getPosicao().equals(posicao)).findFirst().get();
    }

    private GrupoSemaforicoPlano getGrupoSemaforicoPlano(Plano plano, int posicao) {
        return plano.getGruposSemaforicosPlanos().stream().filter(ep -> ep.getGrupoSemaforico().getPosicao().equals(posicao)).findFirst().get();
    }

    private void imprimirListaEstagios(HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss:S");
        listaEstagios.entrySet().stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(entry -> System.out.println(sdf.print(entry.getKey()) + " - " + entry.getValue().getEstagio().getPosicao()));
    }

    private void avancar(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        long quantidade = i * 10L;
        while ((quantidade--) > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    private void avancarAtuado(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        long quantidade = i;
        while ((quantidade--) > 0) {
            gerenciadorDeEstagios.tick();
        }
    }


    public class GrupoCheck {

        private final int grupo;

        private final long inicio;

        private final long fim;

        private final EstadoGrupoSemaforico estado;

        public GrupoCheck(int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public void check(HashMap<DateTime, IntervaloGrupoSemaforico> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).getEstados().get(this.grupo).get(this.inicio));
        }
    }
}

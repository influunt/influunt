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
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends WithInfluuntApplicationNoAuthentication {

    protected DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    protected DateTime inicioExecucao = inicioControlador;

    protected Controlador controlador;

    protected HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios;

    protected HashMap<DateTime, IntervaloGrupoSemaforico> listaHistoricoEstagios;

    protected GerenciadorDeEstagios gerenciadorDeEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.check(listaHistoricoEstagios,inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    protected void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    @NotNull
    protected GerenciadorDeEstagios getGerenciadorDeEstagios(int anel, Plano plano) {
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

    protected Plano getPlano(Anel anel, int posicao) {
        return anel.getPlanos()
                .stream()
                .filter(p -> p.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    protected Plano getPlanoDemandaPrioritaria(Anel anel) {
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

    protected Plano getPlanoDemandaPrioritariaEDispensavel(Anel anel) {
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

    protected Anel getAnel(int posicao) {
        return controlador.getAneis()
                .stream()
                .filter(a -> a.getPosicao().equals(posicao))
                .findFirst()
                .get();
    }

    protected Detector getDetector(Anel anel, int posicao) {
        return anel.getDetectores().stream().filter(det -> det.getPosicao().equals(posicao)).findFirst().get();
    }

    protected EstagioPlano getEstagioPlano(Plano plano, int posicao) {
        return plano.getEstagiosPlanos().stream().filter(ep -> ep.getEstagio().getPosicao().equals(posicao)).findFirst().get();
    }

    protected GrupoSemaforicoPlano getGrupoSemaforicoPlano(Plano plano, int posicao) {
        return plano.getGruposSemaforicosPlanos().stream().filter(ep -> ep.getGrupoSemaforico().getPosicao().equals(posicao)).findFirst().get();
    }

    protected void imprimirListaEstagios(HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss:S");
        listaEstagios.entrySet().stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(entry -> System.out.println(sdf.print(entry.getKey()) + " - " + entry.getValue().getEstagio().getPosicao()));
    }

    protected void avancar(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        long quantidade = i * 10L;
        while ((quantidade--) > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    protected void avancarAtuado(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        long quantidade = i;
        while ((quantidade--) > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    //Métodos auxiliares de modo manual
    protected void acionarModoManual() {
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.ACIONAMENTO_MODO_MANUAL));
    }

    protected void trocarEstagioModoManual() {
        gerenciadorDeEstagios.onEvento(new EventoMotor(inicioExecucao.plus(10000L), TipoEvento.TROCA_ESTAGIO_MANUAL));
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

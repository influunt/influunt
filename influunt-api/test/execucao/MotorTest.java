package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import engine.*;
import integracao.ControladorHelper;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest extends WithInfluuntApplicationNoAuthentication implements MotorCallback {

    protected Controlador controlador;

    protected DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    protected DateTime inicioExecucao = new DateTime(2016, 10, 10, 0, 0, 0);

    protected DateTime instante = new DateTime(2016, 10, 10, 0, 0, 0);

    //Métodos auxiliares de modo manual
    protected void acionarModoManual(Motor motor) {
        motor.onEvento(new EventoMotor(instante, engine.TipoEvento.INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL));
    }

    protected void acionarModoManual(GerenciadorDeEstagios gerenciadorDeEstagios) {
        gerenciadorDeEstagios.onEvento(new EventoMotor(instante, engine.TipoEvento.INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL));
    }

    protected void desativarModoManual(Motor motor) {
        motor.onEvento(new EventoMotor(instante, engine.TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL));
    }

    protected void desativarModoManual(GerenciadorDeEstagios gerenciadorDeEstagios, Plano plano) {
        EventoMotor eventoMotor = new EventoMotor(instante, engine.TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL);
        eventoMotor.setParams(new Object[]{plano});
        gerenciadorDeEstagios.onEvento(eventoMotor);
    }

    protected void trocarEstagioModoManual(Motor motor) {
        motor.onEvento(new EventoMotor(instante, engine.TipoEvento.TROCA_ESTAGIO_MANUAL));
    }

    protected void trocarEstagioModoManual(GerenciadorDeEstagios gerenciadorDeEstagios) {
        gerenciadorDeEstagios.onEvento(new EventoMotor(instante, engine.TipoEvento.TROCA_ESTAGIO_MANUAL));
    }

    protected Anel getAnel(int posicao) {
        return controlador.getAneis()
            .stream()
            .filter(a -> a.getPosicao().equals(posicao))
            .findFirst()
            .get();
    }

    protected Plano getPlano(Anel anel, int posicao) {
        return anel.getPlanos()
            .stream()
            .filter(p -> p.getPosicao().equals(posicao))
            .findFirst()
            .get();
    }

    protected GrupoSemaforico getGrupoSemaforico(int posicao) {
        final GrupoSemaforico[] grupoSemaforico = new GrupoSemaforico[1];
        controlador.getAneis().forEach(anel -> {
            GrupoSemaforico grupo = anel.findGrupoSemaforicoByPosicao(posicao);
            if (grupo != null) {
                grupoSemaforico[0] = grupo;
            }
        });
        return grupoSemaforico[0];
    }

    protected Pair<Integer, TipoDetector> getDetector(Anel anel, int posicao) {
        Detector detector = anel.getDetectores().stream().filter(det -> det.getPosicao().equals(posicao)).findFirst().get();
        return new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
    }

    protected EstagioPlano getEstagioPlano(Plano plano, int posicao) {
        return plano.getEstagiosPlanos().stream().filter(ep -> ep.getEstagio().getPosicao().equals(posicao)).findFirst().get();
    }

    protected GrupoSemaforicoPlano getGrupoSemaforicoPlano(Plano plano, int posicao) {
        return plano.getGruposSemaforicosPlanos().stream().filter(ep -> ep.getGrupoSemaforico().getPosicao().equals(posicao)).findFirst().get();
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

    protected void imprimirListaEstagios(HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss:S");
        listaEstagios.entrySet().stream()
            .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
            .forEach(entry -> System.out.println(sdf.print(entry.getKey()) + " - " + entry.getValue().getEstagio().getPosicao()));
    }

    protected void avancarMilis(Motor motor, long i) {
        long quantidade = i;
        instante = instante.plus(quantidade * 100L);
        while (quantidade-- > 0) {
            try {
                motor.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void avancarSegundos(Motor motor, long i) {
        avancarMilis(motor, i * 10L);
    }

    protected void avancarMinutos(Motor motor, long i) {
        avancarMilis(motor, i * 600L);
    }

    protected void avancarHoras(Motor motor, long i) {
        avancarMilis(motor, i * 36000L);
    }

    protected void avancarDias(Motor motor, long i) {
        avancarMilis(motor, i * 864000L);
    }

    protected void avancar(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        long quantidade = i * 10L;
        instante = instante.plus(quantidade * 100L);
        while ((quantidade--) > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
    }

    @Override
    public void onAlarme(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }

    public class GrupoCheck {

        private final int grupo;

        private final long inicio;

        private final long fim;

        private final EstadoGrupoSemaforico estado;

        private int anel;

        public GrupoCheck(int anel, int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.anel = anel;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public GrupoCheck(int grupo, int inicio, int fim, EstadoGrupoSemaforico estadoGrupoSemaforico) {
            this.grupo = grupo;
            this.inicio = inicio;
            this.fim = fim;
            this.estado = estadoGrupoSemaforico;
        }

        public void checkAnel(HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).get(anel).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).get(anel).getEstados().get(this.grupo).get(this.inicio));
        }

        public void check(HashMap<DateTime, IntervaloGrupoSemaforico> intervalos, DateTime instante) {
            assertNotNull("Mudanca", intervalos.get(instante));
            assertEquals("Comeco", inicio, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().lowerEndpoint().longValue());
            assertEquals("Fim", fim, intervalos.get(instante).getEstados().get(this.grupo).getEntry(this.inicio).getKey().upperEndpoint().longValue());
            assertEquals("Estado", estado, intervalos.get(instante).getEstados().get(this.grupo).get(this.inicio));
        }
    }
}

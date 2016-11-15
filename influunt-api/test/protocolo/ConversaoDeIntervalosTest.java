package protocolo;

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
import os72c.client.device.SerialDevice;
import protocol.Mensagem;
import protocol.MensagemEstagio;
import protocol.TipoDeMensagemBaixoNivel;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ConversaoDeIntervalosTest extends WithInfluuntApplicationNoAuthentication {

    DateTime inicioControlador = new DateTime(2016, 10, 10, 0, 0, 0);

    DateTime inicioExecucao = inicioControlador;

    Controlador controlador;

    HashMap<DateTime, IntervaloGrupoSemaforico> listaEstagios;

    HashMap<DateTime, IntervaloGrupoSemaforico> listaHistoricoEstagios;

    GerenciadorDeEstagios gerenciadorDeEstagios;

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

        avancar(gerenciadorDeEstagios, 100);

        IntervaloGrupoSemaforico intervalo = listaEstagios.get(inicioExecucao);
        MensagemEstagio mensagem = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO,2, 5);
        mensagem.addIntervalos(intervalo);

        byte[] expected = new byte[]{51,3,0,2,5, //header
            33,23,112,0,0,0,0,46,(byte) 224, //Grupo 1
            66,7,  (byte) 208,11,(byte)184,11,(byte)184,39,16, //Grupo 2
            67,31, 64,0,0,0,0,39,16, //Grupo 3
            (byte)196,0,0,19,(byte)136,11,(byte)184,39,16, //Grupo 4
            37,31, 64,0,0,0,0,39,16 //Grupo 5
            ,-96 //Checksum LRC
        };

        assertThat(mensagem.toByteArray(), org.hamcrest.Matchers.equalTo(expected));
        Mensagem msg = Mensagem.toMensagem(mensagem.toByteArray());
        assertThat(expected, org.hamcrest.Matchers.equalTo(msg.toByteArray()));

    }

    @Test
    public void testArduino() {

        Anel anel = getAnel(1);
        Plano plano = getPlano(anel, 7);
        gerenciadorDeEstagios = getGerenciadorDeEstagios(1, plano);

        avancar(gerenciadorDeEstagios, 100);

        IntervaloGrupoSemaforico intervalo = listaEstagios.get(inicioExecucao);
        SerialDevice serialDevice = new SerialDevice();
        serialDevice.sendEstagio(intervalo);

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

        }, null);
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
        i *= 10;
        while (i-- > 0) {
            gerenciadorDeEstagios.tick();
        }
    }

    private void avancarAtuado(GerenciadorDeEstagios gerenciadorDeEstagios, int i) {
        while (i-- > 0) {
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

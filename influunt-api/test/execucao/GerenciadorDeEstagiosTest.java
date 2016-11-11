package execucao;

import engine.*;
import integracao.ControladorHelper;
import models.Plano;
import org.joda.time.DateTime;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeEstagiosTest extends MotorTest {

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
        grupoCheck.check(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    protected void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.check(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    protected GerenciadorDeEstagios getGerenciadorDeEstagios(int anel, Plano plano) {
        return getGerenciadorDeEstagios(anel, plano, null);
    }

    protected GerenciadorDeEstagios getGerenciadorDeEstagios(int anel, Plano plano, Motor motor) {
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

        }, motor);
    }

    protected GerenciadorDeEstagios getGerenciadorDeEstagiosComMotor(int anel, Plano plano, DateTime data) {
        gerenciadorDeEstagios = getGerenciadorDeEstagios(anel, plano, new Motor(controlador, data, data, this));

        gerenciadorDeEstagios.getMotor().tick();
        gerenciadorDeEstagios.getMotor().setEstagios(new ArrayList<>());
        gerenciadorDeEstagios.getMotor().getEstagios().add(gerenciadorDeEstagios);

        return gerenciadorDeEstagios;
    }

}

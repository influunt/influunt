package simulador.akka;

import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import org.joda.time.DateTime;
import simulador.Simulador;

import java.util.List;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimuladorAkka extends Simulador {

    private final SimuladorActor simuladorActor;

    private DateTime ponteiro;

    public SimuladorAkka(SimuladorActor simuladorActor, ParametroSimulacao parametros) {
        super(parametros.getControlador(), parametros);
        this.simuladorActor = simuladorActor;
    }


    @Override
    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.storeEstagio(anel, timestamp.plus(intervalos.getDuracao()), intervalos);
        }
    }

    @Override
    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.storeEstagio(anel, timestamp, intervalos);
        }
    }

    @Override
    public void onCicloEnds(int anel, int numeroCiclos) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.storeTrocaDePlano(timestamp, eventoAnterior, eventoAtual, modos);
        }
    }

    @Override
    public void onAlarme(DateTime timestamp, EventoMotor eventoMotor) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.storeAlarme(timestamp, eventoMotor);
        }
    }

    @Override
    public void onFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void modoManualAtivo(DateTime timestamp) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.ativaModoManual(timestamp);
        }
    }

    @Override
    public void modoManualDesativado(DateTime timestamp) {
        if (timestamp.compareTo(simuladorActor.getPagina()) >= 0) {
            simuladorActor.desativaModoManual(timestamp);
        }
    }

}

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
        super(parametros.getInicioControlador(), parametros.getControlador(), parametros);
        this.simuladorActor = simuladorActor;
    }


    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        simuladorActor.storeEstagio(anel, timestamp, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        simuladorActor.storeTrocaDePlano(timestamp, eventoAnterior, eventoAtual, modos);
    }

    @Override
    public void onAlarme(DateTime timestamp, EventoMotor eventoMotor) {
        simuladorActor.storeAlarme(timestamp, eventoMotor);
    }

    @Override
    public void onFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void modoManualAtivo(DateTime timestamp) {
        simuladorActor.ativaModoManual(timestamp);
    }

    @Override
    public void modoManualDesativado(DateTime timestamp) {
        simuladorActor.desativaModoManual(timestamp);
    }

}

package simulador.akka;

import engine.IntervaloGrupoSemaforico;
import models.simulador.parametros.ParametroSimulacao;
import org.joda.time.DateTime;
import simulador.Simulador;

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
}

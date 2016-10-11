package simulador.akka;

import engine.EstadoGrupoBaixoNivel;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import simulador.eventos.AgendamentoTrocaDePlanoLog;
import simulador.eventos.AlteracaoEventoLog;
import simulador.Simulador;
import models.simulador.parametros.ParametroSimulacao;

import java.util.List;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimuladorAkka extends Simulador {



    private final ParametroSimulacao parametros;

    private final SimuladorActor simuladorActor;

    private DateTime ponteiro;

    public SimuladorAkka(SimuladorActor simuladorActor, ParametroSimulacao parametros){
        super(parametros.getInicioControlador(),parametros.getControlador());
        this.simuladorActor = simuladorActor;
        this.parametros = parametros;
        this.parametros.getDetectores().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getImposicoes().stream().forEach(param -> addEvento(param.toEvento()));
        this.parametros.getFalhas().stream().forEach(param -> addEvento(param.toEvento()));
        this.ponteiro = parametros.getInicioSimulacao();

    }

    public void avancar(int segundos){
        DateTime fim = ponteiro.plusSeconds(segundos);
        simular(ponteiro,fim);
        ponteiro = fim;
    }

    @Override
    public void onStart(DateTime timestamp) {

    }

    @Override
    public void onStop(DateTime timestamp) {

    }

    @Override
    public void onChangeEvento(DateTime timestamp, Evento eventoAntigo, Evento eventoNovo) {
        simuladorActor.storeEvento(new AlteracaoEventoLog(timestamp.minus(dataInicioControlador.getMillis()),eventoAntigo,eventoNovo,1));
    }

    @Override
    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {

    }

    @Override
    public void onAgendamentoTrocaDePlanos(DateTime timestamp, DateTime momentoAgendamento, int anel, int plano, int planoAnterior) {
        simuladorActor.storeEvento(new AgendamentoTrocaDePlanoLog(timestamp.minus(dataInicioControlador.getMillis()),(momentoAgendamento.minus(dataInicioControlador.getMillis())),anel,plano,planoAnterior));
    }

    @Override
    public void onEstado(DateTime timeStamp, EstadoGrupoBaixoNivel estado) {
        simuladorActor.storeEstado(timeStamp.minus(dataInicioControlador.getMillis()),estado);
    }

}

package simulacao;

import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import models.Controlador;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import org.joda.time.DateTime;
import simulador.Simulador;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 1/5/17.
 */
public class BasicSimuladorTest extends Simulador {
    public List<DateTime> listaModoManualAtivo = new ArrayList<>();

    public List<DateTime> listaModoManualDesativado = new ArrayList<>();

    public List<DateTime> listaTrocaManualLiberada = new ArrayList<>();

    public List<DateTime> listaTrocaManualBloqueada = new ArrayList<>();

    public BasicSimuladorTest(Controlador controlador, ParametroSimulacao parametros) {
        super(controlador, parametros);
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
    public void modoManualAtivo(DateTime timestamp) {
        listaModoManualAtivo.add(timestamp);
    }

    @Override
    public void modoManualDesativado(DateTime timestamp) {
        listaModoManualDesativado.add(timestamp);
    }

    @Override
    public void trocaEstagioManualLiberada(DateTime timestamp) {
        listaTrocaManualLiberada.add(timestamp);
    }

    @Override
    public void trocaEstagioManualBloqueada(DateTime timestamp) {
        listaTrocaManualBloqueada.add(timestamp);
    }

    @Override
    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

    }

    @Override
    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

    }

    @Override
    public void onCicloEnds(int anel, int numeroCiclos) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

    }

}

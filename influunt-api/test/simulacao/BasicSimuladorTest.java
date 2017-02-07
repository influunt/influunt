package simulacao;

import engine.AgendamentoTrocaPlano;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import models.Controlador;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import simulador.Simulador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodrigosol on 1/5/17.
 */
public class BasicSimuladorTest extends Simulador {
    public List<DateTime> listaModoManualAtivo = new ArrayList<>();

    public List<DateTime> listaModoManualDesativado = new ArrayList<>();

    public List<DateTime> listaTrocaManualLiberada = new ArrayList<>();

    public List<DateTime> listaTrocaManualBloqueada = new ArrayList<>();

    public HashMap<Integer, List<Pair<Long, IntervaloGrupoSemaforico>>> estagios = new HashMap();

    public HashMap<Integer, List<Pair<Long, IntervaloGrupoSemaforico>>> estagiosEnds = new HashMap();

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
        if (!estagios.containsKey(anel)) {
            estagios.put(anel, new ArrayList<>());
        }
        estagios.get(anel).add(new Pair<Long, IntervaloGrupoSemaforico>(tempoDecorrido, intervalos));
    }

    @Override
    public void onEstagioModify(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

    }

    @Override
    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!estagiosEnds.containsKey(anel)) {
            estagiosEnds.put(anel, new ArrayList<>());
        }
        estagiosEnds.get(anel).add(new Pair<Long, IntervaloGrupoSemaforico>(tempoDecorrido, intervalos));
    }

    @Override
    public void onCicloEnds(int anel, int numeroCiclos, Long tempoDecorrido) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

    }

    @Override
    public void onImposicaoPlano(int anel) {

    }

    @Override
    public void onLiberacaoImposicao(int anel) {

    }

}

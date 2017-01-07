package execucao;

import engine.AgendamentoTrocaPlano;
import engine.IntervaloGrupoSemaforico;
import integracao.ControladorHelper;
import models.Evento;
import models.Plano;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class GerenciadorDeTrocasTest extends MotorTest {

    protected HashMap<DateTime, Evento> listaTrocaPlano;

    protected HashMap<DateTime, HashMap<Integer, Pair<Evento, Plano>>> listaTrocaPlanoEfetiva;

    protected HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaEstagios;

    protected HashMap<DateTime, HashMap<Integer, IntervaloGrupoSemaforico>> listaHistoricoEstagios;

    @Before
    public void setup() {
        controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        controlador.save();
        listaTrocaPlano = new HashMap<>();
        listaTrocaPlanoEfetiva = new HashMap<>();
        listaEstagios = new HashMap<>();
        listaHistoricoEstagios = new HashMap<>();
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        listaTrocaPlano.put(timestamp, eventoAtual);
    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {
        if (!listaTrocaPlanoEfetiva.containsKey(agendamentoTrocaPlano.getMomentoDaTroca())) {
            listaTrocaPlanoEfetiva.put(agendamentoTrocaPlano.getMomentoDaTroca(), new HashMap<>());
        }
        listaTrocaPlanoEfetiva.get(agendamentoTrocaPlano.getMomentoDaTroca()).put(agendamentoTrocaPlano.getAnel(), new Pair<Evento, Plano>(agendamentoTrocaPlano.getEvento(), agendamentoTrocaPlano.getPlano()));
    }


    @Override
    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!listaEstagios.containsKey(timestamp)) {
            listaEstagios.put(timestamp, new HashMap<>());
        }
        listaEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if (!listaHistoricoEstagios.containsKey(timestamp)) {
            listaHistoricoEstagios.put(timestamp, new HashMap<>());
        }
        listaHistoricoEstagios.get(timestamp).put(anel, intervalos);
    }

    @Override
    public void onCicloEnds(int anel, int numeroCiclos) {

    }

    protected Plano getPlanoTrocaEfetiva(int anel, int tempo, int tempo2) {
        return listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(tempo).plus(tempo2)).get(anel).getSecond();
    }

    protected Plano getPlanoTrocaEfetiva(int anel, int tempo) {
        return listaTrocaPlanoEfetiva.get(inicioExecucao.plusSeconds(tempo)).get(anel).getSecond();
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.checkAnel(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset));
    }

    protected void verificaHistoricoGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.checkAnel(listaHistoricoEstagios, inicioExecucao.plusSeconds(offset).plus(offset2));
    }

    protected void verificaGruposSemaforicos(int offset, GrupoCheck grupoCheck) {
        grupoCheck.checkAnel(listaEstagios, inicioExecucao.plusSeconds(offset));
    }

    protected void verificaGruposSemaforicos(int offset, int offset2, GrupoCheck grupoCheck) {
        grupoCheck.checkAnel(listaEstagios, inicioExecucao.plusSeconds(offset).plus(offset2));
    }


}

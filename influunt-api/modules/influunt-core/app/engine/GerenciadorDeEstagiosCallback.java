package engine;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface GerenciadorDeEstagiosCallback {

    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onCicloEnds(int anel, int numeroCiclos);

    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano);

}

package engine;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface GerenciadorDeEstagiosCallback {

    public void onEstagioChange(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onEstagioModify(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onEstagioEnds(int anel, int numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onCicloEnds(int anel, int numeroCiclos, Long tempoDecorrido);

    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano);

    public void onImposicaoPlano(int anel);

    public void onLiberacaoImposicao(int anel);
}

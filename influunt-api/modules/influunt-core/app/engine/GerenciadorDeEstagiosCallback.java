package engine;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface GerenciadorDeEstagiosCallback {

    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

    public void onCicloEnds(int anel, Long numeroCiclos);

}

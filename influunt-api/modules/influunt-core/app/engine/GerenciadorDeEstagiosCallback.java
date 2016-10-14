package engine;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface GerenciadorDeEstagiosCallback {

    public void onEstagioChange(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);
    public void onEstagioEnds(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos);

}

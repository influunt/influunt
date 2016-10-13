package engine;

import models.EstagioPlano;
import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 10/11/16.
 */
public interface GerenciadorDeEstagiosCallback {

    public void onChangeEstagio(Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoNovo);
}

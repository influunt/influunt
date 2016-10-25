package engine;

import org.joda.time.DateTime;

/**
 * Created by rodrigosol on 9/29/16.
 */
public interface GerenciadorDeIntervalosCallBack {
    public void onAgendamentoDeTrocaDePlanos(DateTime timestamp, DateTime momento, int anel, int plano, int planoAnterior);
}

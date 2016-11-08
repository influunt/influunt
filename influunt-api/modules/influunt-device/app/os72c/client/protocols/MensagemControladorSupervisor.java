package os72c.client.protocols;

import engine.IntervaloGrupoSemaforico;
import os72c.client.models.TipoEvento;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class MensagemControladorSupervisor extends Mensagem {
    public final TipoEvento tipoEvento;

    public final String[] argumentos;

    public final IntervaloGrupoSemaforico intervaloGrupoSemaforico;

    public MensagemControladorSupervisor(TipoEvento tipoEvento, IntervaloGrupoSemaforico intervaloGrupoSemaforico, String... args) {
        this.tipoEvento = tipoEvento;
        this.intervaloGrupoSemaforico = intervaloGrupoSemaforico;
        this.argumentos = args;
    }

}

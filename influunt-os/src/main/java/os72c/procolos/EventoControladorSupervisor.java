package os72c.procolos;

import os72c.models.EstadoGrupo;

import java.util.Date;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class EventoControladorSupervisor extends Mensagem {
    public final TipoEvento tipoEvento;
    public final String[] argumentos;
    public final EstadoGrupo[] estadoDosGrupos;

    public EventoControladorSupervisor(TipoEvento tipoEvento, EstadoGrupo[] estadoDosGrupos, String... args){
        this.tipoEvento = tipoEvento;
        this.estadoDosGrupos = estadoDosGrupos;
        this.argumentos = args;
    }

    @Override
    public String toString() {
        return "@id=" + id + ",@timestamp=" + timestamp + ",@datetime=" + new Date(timestamp) +
                ",@tipoEvento=" + tipoEvento + ",@args:" + argumentos;
    }
}

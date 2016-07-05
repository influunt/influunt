package os72c.supervisores;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActorContext;
import os72c.models.EstadoGrupo;
import os72c.procolos.EventoControladorSupervisor;
import os72c.procolos.TipoEvento;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Estado {
    private Long timestap;
    private Cancellable cancellable;

    public Long getTimestap() {
        return timestap;
    }

    public void setTimestap(Long timestap) {
        this.timestap = timestap;
    }

    public Cancellable getCancellable() {
        return cancellable;
    }

    public void setCancellable(Cancellable cancellable) {
        this.cancellable = cancellable;
    }

    public void agendar(UntypedActorContext context, ActorRef controlador, EstadoGrupo[] estadoGrupos, int duracao) {

        setCancellable(context.system().scheduler().scheduleOnce( Duration.create(duracao, TimeUnit.MILLISECONDS),
                controlador, new EventoControladorSupervisor(TipoEvento.MUDANCA_GRUPOS,estadoGrupos), context.dispatcher(), null));
    }
}

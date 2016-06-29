package os.cet.driver.supervisores;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActorContext;
import os.cet.driver.models.EstadoGrupo;
import os.cet.driver.protocolo.EventoControladorSupervisor;
import os.cet.driver.protocolo.TipoEvento;
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

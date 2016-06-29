package os.cet.driver.supervisores;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import os.cet.driver.models.EstadoGrupo;
import os.cet.driver.controladores.ControladorArduino;
import os.cet.driver.models.Intervalos;
import os.cet.driver.models.Troca;
import os.cet.driver.protocolo.EventoControladorSupervisor;
import os.cet.driver.protocolo.TipoEvento;

/**
 * Created by rodrigosol on 6/24/16.
 */
public class Supervisor extends UntypedActor {
    private Intervalos intervalos;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef controlador;
    private ImmutableList<Troca> trocas;

    @Override
    public void preStart() throws Exception {
        controlador = getContext().actorOf(Props.create(ControladorArduino.class), "controlador");
        intervalos = new Intervalos(getContext(),controlador,getSelf());
        controlador.tell(new EventoControladorSupervisor(TipoEvento.SUPERVISOR_PRONTO,null),getSelf());
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof EventoControladorSupervisor){
            final EventoControladorSupervisor eventoControladorSupervisor = (EventoControladorSupervisor) message;
            System.out.println(eventoControladorSupervisor);
            log.debug(eventoControladorSupervisor.toString());
            switch (eventoControladorSupervisor.tipoEvento){
                case CONTROLADOR_PRONTO:
                    registraControlador(eventoControladorSupervisor);
                    break;
                case PROXIMO_CICLO:
                    intervalos.proximoCiclo(Integer.valueOf(eventoControladorSupervisor.argumentos[0]));
                    break;
            }
        }
    }

    private void registraControlador(EventoControladorSupervisor eventoControladorSupervisor) {
        trocas = ImmutableList.of(new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERDE, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.AMARELHO, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(12000,EstadoGrupo.VERDE,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(4000,EstadoGrupo.AMARELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(12000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERDE),
                                  new Troca(8000,EstadoGrupo.VERMELHO,EstadoGrupo.VERDE, EstadoGrupo.VERDE)

        );



        intervalos.setTrocas(trocas);
        intervalos.start(0);

    }
}

package os72c.controladores;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import os72c.models.EstadoGrupo;
import os72c.procolos.EventoControladorSupervisor;
import os72c.procolos.TipoEvento;
import scala.Int;

/**
 * Created by rodrigosol on 6/24/16.
 */
public abstract class Controlador extends UntypedActor {

    private static final int ID_CONTROLADOR = 0;
    protected ActorRef supervisor;
    private String idControlador;


    protected abstract void supervisorPronto(ActorRef supervisor, String[] argumentos);

    protected void pronto(String fabricante, String modelo, String firmware) {
        supervisor.tell(new EventoControladorSupervisor(TipoEvento.CONTROLADOR_PRONTO,null,idControlador,fabricante,modelo,firmware),getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Recebida:" + message);
        if(message instanceof EventoControladorSupervisor) {
            EventoControladorSupervisor eventoControladorSupervisor = (EventoControladorSupervisor) message;
            switch (eventoControladorSupervisor.tipoEvento){
                case MUDANCA_GRUPOS:
                    onChange(eventoControladorSupervisor.estadoDosGrupos, Integer.valueOf(eventoControladorSupervisor.argumentos[0]));
                    break;
                case SUPERVISOR_PRONTO:
                    supervisor = getSender();
                    supervisorPronto(supervisor,eventoControladorSupervisor.argumentos);
                    break;
                case AMARELHO_INTERMITENTE:
                    entrarEmModoAmarelhoIntermitente();
                    break;
            }
        }
    }

    protected abstract void entrarEmModoAmarelhoIntermitente();

    protected abstract void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante);

}

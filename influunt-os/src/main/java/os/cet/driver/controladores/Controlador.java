package os.cet.driver.controladores;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import os.cet.driver.models.EstadoGrupo;
import os.cet.driver.protocolo.EventoControladorSupervisor;
import os.cet.driver.protocolo.TipoEvento;

/**
 * Created by rodrigosol on 6/24/16.
 */
public abstract class Controlador extends UntypedActor {

    private static final int ID_CONTROLADOR = 0;
    protected ActorRef supervisor;
    private String idControlador;

    protected abstract void supervisorPronto(ActorRef supervisor);

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
                    onChange(eventoControladorSupervisor.estadoDosGrupos);
                    break;
                case SUPERVISOR_PRONTO:
                    supervisor = getSender();
                    supervisorPronto(supervisor);
                    break;
                case AMARELHO_INTERMITENTE:
                    entrarEmModoAmarelhoIntermitente();
                    break;
            }
        }
    }

    protected abstract void entrarEmModoAmarelhoIntermitente();

    protected abstract void onChange(EstadoGrupo[] estadoDosGrupos);

}

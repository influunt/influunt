package os72c.controladores;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import jssc.SerialPortException;
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
            }
        }
    }

    protected void trap(String code){

        TipoEvento tipoEvento = TipoEvento.INDEFINIDO;
        char type = code.charAt(0);
        String index = String.valueOf(code.charAt(1));
        if(type == 'P'){
            tipoEvento = TipoEvento.DETECTOR_PEDESTRE;
        }else if(type == 'V'){
            tipoEvento = TipoEvento.DETECTOR_VEICULAR;
        }else if(type == 'E'){
            tipoEvento = TipoEvento.FALHA;
            try {
                entrarEmModoAmarelhoIntermitente();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }

        }else if(type == 'M'){
            tipoEvento = TipoEvento.MUDANCA_MANUAL;
        }
        supervisor.tell(new EventoControladorSupervisor(tipoEvento,null,index),getSelf());
    }

    protected abstract void entrarEmModoAmarelhoIntermitente() throws SerialPortException;

    protected abstract void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante) throws Exception;

}

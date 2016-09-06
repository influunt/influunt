package os72c.client.controladores;

import akka.actor.ActorRef;
import os72c.client.models.EstadoGrupo;


public class ControladorI2C extends Controlador {

    @Override
    protected void supervisorPronto(ActorRef supervisor, String[] argumentos) {
        System.out.println("Supervisor Pronto");
        pronto("Raro Labs","FakeController","1.00");
    }

    @Override
    protected void entrarEmModoAmarelhoIntermitente() {
        System.out.println("Entrei em modo amarelho Intermitente");
    }

    @Override
    protected void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante) {
        System.out.println("onChange");
        for (int i = 0; i < estadoDosGrupos.length; i++) {
            if (estadoDosGrupos[i] != null) {
                System.out.println("G" + (i + 1) + " --> " + estadoDosGrupos[i]);
            }
        }
    }
}

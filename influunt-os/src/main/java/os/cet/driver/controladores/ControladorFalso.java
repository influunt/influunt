package os.cet.driver.controladores;

import akka.actor.ActorRef;
import os.cet.driver.models.EstadoGrupo;


public class ControladorFalso extends Controlador {

    @Override
    protected void supervisorPronto(ActorRef supervisor) {
        System.out.println("Supervisor Pronto");
        pronto("Raro Labs","FakeController","1.00");
    }

    @Override
    protected void entrarEmModoAmarelhoIntermitente() {
        System.out.println("Entrei em modo amarelho Intermitente");
    }

    @Override
    protected void onChange(EstadoGrupo[] estadoDosGrupos) {
        System.out.println("onChange");
        for (int i = 0; i < estadoDosGrupos.length; i++) {
            if (estadoDosGrupos[i] != null) {
                System.out.println("G" + (i + 1) + " --> " + estadoDosGrupos[i]);
            }
        }
    }
}

package os.cet.driver.models;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;
import com.google.common.collect.ImmutableList;
import os.cet.driver.protocolo.EventoControladorSupervisor;
import os.cet.driver.protocolo.TipoEvento;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class Intervalos {

    private final UntypedActorContext context;
    private final ActorRef controlador;
    private ImmutableList<Troca> trocas;
    private final ActorRef supervisor;
    private int intervalo = 0;

    public Intervalos(UntypedActorContext context, ActorRef controlador, ActorRef supervisor) {
        this.context = context;
        this.controlador = controlador;
        this.supervisor = supervisor;
    }

    public void start(int delay) {
        int ciclo = 0;
        int momento = 0;
        for(int i =0; i < trocas.size(); i++){
            Troca troca = trocas.get(i);
            if(i == 0){
                momento = delay;
            }else{
                momento = delay + ciclo;
            }

            troca.setCancellable(context.system().scheduler().scheduleOnce(Duration.create(momento, TimeUnit.MILLISECONDS),
                    new Runnable() {
                        @Override
                        public void run() {
                            controlador.tell(new EventoControladorSupervisor(TipoEvento.MUDANCA_GRUPOS,troca.getGrupos()), supervisor);
                            intervalo++;
                            if(intervalo >= trocas.size()){
                                intervalo = 0;
                                supervisor.tell(new EventoControladorSupervisor(TipoEvento.PROXIMO_CICLO,null,troca.getDuracao().toString()),supervisor);
                            }


                        }
                    }, context.dispatcher())
            );
            ciclo += troca.getDuracao();
        }

    }

    public void setTrocas(ImmutableList<Troca> trocas) {
        this.trocas = trocas;
    }

    public void proximoCiclo(Integer delay) {
        start(delay);
    }
}

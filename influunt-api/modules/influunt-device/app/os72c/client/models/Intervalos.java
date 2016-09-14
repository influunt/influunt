package os72c.client.models;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;
import com.google.common.collect.ImmutableList;
import os72c.client.protocols.MensagemControladorSupervisor;
import scala.concurrent.duration.Duration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class Intervalos {

    private final UntypedActorContext context;

    private final ActorRef controlador;

    private final ActorRef supervisor;

    private ImmutableList<Troca> trocas;

    private int intervalo = 0;

    public Intervalos(UntypedActorContext context, ActorRef controlador, ActorRef supervisor) {
        this.context = context;
        this.controlador = controlador;
        this.supervisor = supervisor;
    }

    public void start(int delay, int start) {
        int ciclo = 0;
        int momento = 0;
        for (int i = start; i < trocas.size(); i++) {
            Troca troca = trocas.get(i);
            if (i == 0) {
                momento = delay;
            } else {
                momento = delay + ciclo;
            }
            troca.setCancellable(context.system().scheduler().scheduleOnce(Duration.create(momento, TimeUnit.MILLISECONDS),
                    new Runnable() {
                        @Override
                        public void run() {
                            controlador.tell(new MensagemControladorSupervisor(TipoEvento.MUDANCA_GRUPO, troca.getGrupos(), String.valueOf(new Date().getTime()), String.valueOf(troca.getDuracao())), supervisor);
                            intervalo++;
                            if (intervalo >= trocas.size()) {
                                intervalo = 0;
                                supervisor.tell(new MensagemControladorSupervisor(TipoEvento.PROXIMO_CICLO, null, troca.getDuracao().toString()), supervisor);
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
        start(delay, 0);
    }

    public void proximoEstagio() {
        pararExecucao();
        int proximo = intervalo + 1 >= trocas.size() ? 0 : intervalo + 1;
        System.out.println("Pulando para o intervalo:" + proximo);
        start(0, proximo);
    }

    public void status() {
        System.out.println("Intervalo Atual:" + intervalo);
    }

    public void pararExecucao() {
        trocas.stream().forEach(troca -> {
                    troca.getCancellable().cancel();
                }
        );
    }
}

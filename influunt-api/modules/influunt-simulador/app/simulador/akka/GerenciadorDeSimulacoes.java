package simulador.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.simulador.parametros.ParametroSimulacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 10/4/16.
 */
@Singleton
public class GerenciadorDeSimulacoes {

    private final ActorSystem system;

    private ActorRef servidor;

    private List<ActorRef> simuladores = new ArrayList<>();

    @Inject
    public GerenciadorDeSimulacoes(ActorSystem system) {
        this.system = system;
    }

    public void finish() {
        system.terminate();
    }

    public void iniciarSimulacao(ParametroSimulacao params) {
        ActorRef simulador = system.actorOf(Props.create(SimuladorActor.class,
            "mosquitto.rarolabs.com.br",
            "1883",
            params), "simulador_" + params.getId().toString());

        simuladores.add(simulador);
    }
}

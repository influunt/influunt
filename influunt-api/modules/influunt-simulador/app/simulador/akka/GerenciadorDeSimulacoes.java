package simulador.akka;

import akka.actor.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.simulador.parametros.ParametroSimulacao;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 10/4/16.
 */
@Singleton
public class GerenciadorDeSimulacoes {

    private final ActorSystem system;

    private ActorRef servidor;

    private List<ActorRef> simuladores = new ArrayList<>();

    @Inject
    private Configuration configuration;

    public GerenciadorDeSimulacoes() {
        this.system = ActorSystem.create("SimuladorSystem-"+ UUID.randomUUID().toString());
    }

//    public void finish() {
//        system.terminate();
//    }

    public void pararSimulacao(String simulacaoId) {
        System.out.println("System: " + system.name());
        ActorSelection simulador = system.actorSelection("simulador_" + simulacaoId);
        system.stop(simulador.anchor());
//        simulador.tell(Kill.getInstance(), null);
//        simulador.tell("kill!", simulador.anchor());
    }

    public void iniciarSimulacao(ParametroSimulacao params) {
        Configuration mqtt = configuration.getConfig("central").getConfig("mqtt");
        ActorRef simulador = system.actorOf(Props.create(SimuladorActor.class,
            mqtt.getString("host"),
            mqtt.getString("port"),
            mqtt.getString("login"),
            mqtt.getString("senha"),
            params), "simulador_" + params.getId().toString());

        simuladores.add(simulador);
    }
}

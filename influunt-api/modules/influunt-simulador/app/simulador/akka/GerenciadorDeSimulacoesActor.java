package simulador.akka;

import akka.actor.*;
import akka.japi.Function;
import models.simulador.parametros.ParametroSimulacao;
import play.Logger;
import scala.concurrent.duration.Duration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pedropires on 12/15/16.
 */
public class GerenciadorDeSimulacoesActor extends UntypedActor {

    private static OneForOneStrategy strategy =
        new OneForOneStrategy(-1, Duration.Inf(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    Logger.warn(t.getMessage());
                    Logger.warn(Arrays.toString(t.getStackTrace()));
                    return SupervisorStrategy.resume();
                }
            }, false);

    private final String mqttHost;

    private final String mqttPort;

    private final String mqttLogin;

    private final String mqttPassword;

    private Map<String, ActorRef> simulacoes = new HashMap<>();

    public GerenciadorDeSimulacoesActor(String mqttHost, String mqttPort, String mqttLogin, String mqttPassword) {
        this.mqttHost = mqttHost;
        this.mqttPort = mqttPort;
        this.mqttLogin = mqttLogin;
        this.mqttPassword = mqttPassword;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof ParametroSimulacao) {
            ParametroSimulacao config = (ParametroSimulacao) message;
            ActorRef simulador = context().actorOf(Props.create(SimuladorActor.class,
                mqttHost,
                mqttPort,
                mqttLogin,
                mqttPassword,
                config), "simulador_" + config.getId().toString());
            simulacoes.put(config.getId().toString(), simulador);
        } else if (message instanceof String) {
            getContext().stop(simulacoes.get(message.toString()));
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}

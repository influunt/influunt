package simulador.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.simulador.parametros.ParametroSimulacao;
import play.Configuration;

/**
 * Created by rodrigosol on 10/4/16.
 */
@Singleton
public class GerenciadorDeSimulacoes {

    private ActorRef gerenciadorActor;

    @Inject
    public GerenciadorDeSimulacoes(Configuration configuration, ActorSystem system) {
        Configuration mqtt = configuration.getConfig("central").getConfig("mqtt");
        gerenciadorActor = system.actorOf(Props.create(GerenciadorDeSimulacoesActor.class,
            mqtt.getString("host"),
            mqtt.getString("port"),
            mqtt.getString("login"),
            mqtt.getString("senha")), "GerenciadorDeSimulacoes");
    }

    public void pararSimulacao(String simulacaoId) {
        gerenciadorActor.tell(simulacaoId, ActorRef.noSender());
    }

    public void iniciarSimulacao(ParametroSimulacao params) {
        gerenciadorActor.tell(params, ActorRef.noSender());
    }
}

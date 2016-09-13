package os72c.client.conn;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import os72c.client.handlers.ConfiguracaoActorHandler;
import os72c.client.handlers.EchoActorHandler;
import os72c.client.protocols.Mensagem;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import protocol.Envelope;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class MessageBroker extends UntypedActor {


    Router routerEcho;
    ActorRef actorConfiguracao;


    public MessageBroker(String idControlador) {
        List<Routee> routeesEcho = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef rEcho = getContext().actorOf(Props.create(EchoActorHandler.class));
            getContext().watch(rEcho);
            routeesEcho.add(new ActorRefRoutee(rEcho));
        }
        routerEcho = new Router(new RoundRobinRoutingLogic(), routeesEcho);

        actorConfiguracao = getContext().actorOf(Props.create(ConfiguracaoActorHandler.class, idControlador));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()) {
                case ECHO:
                    routerEcho.route(envelope, getSender());
                    break;
                case CONFIGURACAO:
                    System.out.println("CONFIGURACAO");
                    actorConfiguracao.tell(envelope, getSender());
                    break;
                case ERRO:
                    System.out.println("ERRO");
                    break;

            }

        } else if (message instanceof Mensagem) {
            if(message instanceof MensagemVerificaConfiguracao){
                actorConfiguracao.tell("VERIFICA", getSender());
            }
        }
    }
}

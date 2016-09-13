package server.conn;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import handlers.ConexaoOfflineActorHandler;
import handlers.ConexaoOnlineActorHandler;
import handlers.ConfiguracaoActorHandler;
import handlers.EchoActorHandler;
import protocol.Envelope;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class MessageBroker extends UntypedActor {


    Router routerControladorOnline;

    Router routerControladorOffline;

    Router routerEcho;

    Router routerConfiguracaoInicial;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    {
        List<Routee> routeesControladorOnline = new ArrayList<Routee>();
        List<Routee> routeesControladorOffline = new ArrayList<Routee>();
        List<Routee> routeesEcho = new ArrayList<Routee>();
        List<Routee> routeesConfiguracaoInicial = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef rControladorOnline = getContext().actorOf(Props.create(ConexaoOnlineActorHandler.class));
            getContext().watch(rControladorOnline);
            routeesControladorOnline.add(new ActorRefRoutee(rControladorOnline));

            ActorRef rControladorOffline = getContext().actorOf(Props.create(ConexaoOfflineActorHandler.class));
            getContext().watch(rControladorOffline);
            routeesControladorOffline.add(new ActorRefRoutee(rControladorOffline));

            ActorRef rEcho = getContext().actorOf(Props.create(EchoActorHandler.class));
            getContext().watch(rEcho);
            routeesEcho.add(new ActorRefRoutee(rEcho));

            ActorRef rConfiguracao = getContext().actorOf(Props.create(ConfiguracaoActorHandler.class));
            getContext().watch(rConfiguracao);
            routeesConfiguracaoInicial.add(new ActorRefRoutee(rConfiguracao));

        }
        routerControladorOnline = new Router(new RoundRobinRoutingLogic(), routeesControladorOnline);
        routerControladorOffline = new Router(new RoundRobinRoutingLogic(), routeesControladorOffline);
        routerEcho = new Router(new RoundRobinRoutingLogic(), routeesEcho);
        routerConfiguracaoInicial = new Router(new RoundRobinRoutingLogic(), routeesConfiguracaoInicial);
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()) {
                case CONTROLADOR_ONLINE:
                    routerControladorOnline.route(envelope, getSender());
                    break;
                case CONTROLADOR_OFFLINE:
                    routerControladorOffline.route(envelope, getSender());
                    break;
                case ECHO:
                    routerEcho.route(envelope, getSender());
                    break;
                case CONFIGURACAO_INICIAL:
                    System.out.println("CONFIGURACAO_INICIAL");
                    routerConfiguracaoInicial.route(envelope, getSender());
                    break;
            }

        }
    }
}

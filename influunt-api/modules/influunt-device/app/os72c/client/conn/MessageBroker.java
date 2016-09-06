package os72c.client.conn;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import os72c.client.handlers.EchoActorHandler;
import protocol.Envelope;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class MessageBroker extends UntypedActor {


    Router routerEcho;
    {
        List<Routee> routeesEcho = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef rEcho = getContext().actorOf(Props.create(EchoActorHandler.class));
            getContext().watch(rEcho);
            routeesEcho.add(new ActorRefRoutee(rEcho));
        }
        routerEcho = new Router(new RoundRobinRoutingLogic(), routeesEcho);
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Envelope){
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()){
                case ECHO:
                    routerEcho.route(envelope,getSender());
                    break;
            }

        }
    }
}

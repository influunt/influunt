package utils;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 9/14/16.
 */
public class MessageBrokerUtils {
    public static Router createRoutees(UntypedActorContext context, int size, Class clazz, Object... params) {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < size; i++) {
            ActorRef ref;
            if (params.length > 0) {
                ref = context.actorOf(Props.create(clazz, params));
            } else {
                ref = context.actorOf(Props.create(clazz, params));
            }
            context.watch(ref);
            routees.add(new ActorRefRoutee(ref));
        }

        return new Router(new RoundRobinRoutingLogic(), routees);
    }

}

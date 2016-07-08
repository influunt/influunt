package os72c.central;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import os72c.ProxyParent;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 7/5/16.
 */
public class CentralActor extends UntypedActor {

    Cluster cluster = Cluster.get(getContext().system());
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Long i = 0l;

    List<ActorRef> controladores = new ArrayList<ActorRef>();
    private String endereco;
    private ActorRef proxy;

    //subscribe to cluster changes
    @Override
    public void preStart() {
        //#subscribe
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class, ClusterEvent.MemberUp.class);
        //#subscribe
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) {
        log.info("-----------------------------------------------------------");
        log.info(message.toString());
        log.info("-----------------------------------------------------------");
        if (message instanceof ClusterEvent.MemberUp) {
            log.info("-----------------------------------------------------------");
            log.info("Membro adicionado");


            endereco = ((ClusterEvent.MemberUp) message).member().address().toString();
            ((ClusterEvent.MemberUp) message).member().uniqueAddress();
            log.info("Endereco:" + endereco);
            proxy = getContext().actorOf(Props.create(ProxyParent.class, getContext().actorSelection(endereco + "/user/supervisor").anchorPath()));
            proxy.tell("Ola from mac", getSelf());
        }else if(message instanceof Long) {
            log.info("-----------------------------------------------------------");
            log.info("----> {}", message);
            log.info("-----------------------------------------------------------");
            i+=1;
            proxy.tell(i,getSelf());
            proxy.tell("Ola from central",getSelf());
        }

//            ClusterEvent.MemberUp mUp = (ClusterEvent.MemberUp) message;
//            getSender().tell("oi",getSelf());
//        } else if (message instanceof ClusterEvent.UnreachableMember) {
//            ClusterEvent.UnreachableMember mUnreachable = (ClusterEvent.UnreachableMember) message;
//            log.info("Member detected as unreachable: {}", mUnreachable.member());
//
//        } else if (message instanceof ClusterEvent.MemberRemoved) {
//            ClusterEvent.MemberRemoved mRemoved = (ClusterEvent.MemberRemoved) message;
//            log.info("Member is Removed: {}", mRemoved.member());
//
//        } else if (message.equals("SupervisorRegistration")) {
//            getContext().watch(getSender());
//            controladores.add(getSender());
//            log.info("Controlador adicionado");
//            getSender().tell("Obrigado!",getSelf());
//
//        }

        //} else {
//            unhandled(message);
//        }

    }

}

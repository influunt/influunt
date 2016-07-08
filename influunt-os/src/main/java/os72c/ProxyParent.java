package os72c;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.ReliableProxy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


public class ProxyParent extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef proxy;


  public ProxyParent(ActorPath targetPath) {
      log.info("TargetPath: {}",targetPath );
      proxy = getContext().actorOf(
          ReliableProxy.props(targetPath,
              Duration.create(1000, TimeUnit.MILLISECONDS)));

      getContext().actorFor(targetPath).tell("Ola from hell",getSelf());
    }
 
    public void onReceive(Object msg) {
        log.info("Recebida: {}",msg );
        log.info("Actor: {}",getSender().toString() );
        proxy.forward(msg, getContext());
    }
  }
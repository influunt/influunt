package os72c;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.FSM;
import akka.actor.UntypedActor;
import akka.contrib.pattern.ReliableProxy;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ProxyTransitionParent extends UntypedActor {
  private final ActorRef proxy;
  private ActorRef client = null;
 
  public ProxyTransitionParent(ActorPath targetPath) {
    proxy = getContext().actorOf(
        ReliableProxy.props(targetPath,
            Duration.create(100, TimeUnit.MILLISECONDS)));
    proxy.tell(new FSM.SubscribeTransitionCallBack(getSelf()), getSelf());
  }
 
  public void onReceive(Object msg) {
    if ("hello".equals(msg)) {
      proxy.tell("world!", getSelf());
      client = getSender();
    } else if (msg instanceof FSM.CurrentState<?>) {
      // get initial state
    } else if (msg instanceof FSM.Transition<?>) {
      @SuppressWarnings("unchecked")
      final FSM.Transition<ReliableProxy.State> transition =
        (FSM.Transition<ReliableProxy.State>) msg;
      assert transition.fsmRef().equals(proxy);
      if (transition.from().equals(ReliableProxy.active()) &&
              transition.to().equals(ReliableProxy.idle())) {
        client.tell("done", getSelf());
      }
    }
  }
}
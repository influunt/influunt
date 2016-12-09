package handlers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import status.PacoteTransacao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class PacoteTransacaoActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef manager;

    private PacoteTransacao pacoteTransacao;

    private Map<String, ActorRef> transacoes = new HashMap<>();

    public PacoteTransacaoActorHandler(PacoteTransacao pacoteTransacao, ActorRef ref) {
        this.pacoteTransacao = pacoteTransacao;
        this.manager = ref;

        start();
    }

    private void start() {
        pacoteTransacao.getTransacoes().stream().forEach(transacao -> {
            ActorRef ref = getContext().actorOf(Props.create(TransacaoActorHandler.class, transacao, getSelf()), "transacao-" + transacao.getTransacaoId());
            transacoes.put(transacao.getTransacaoId(), ref);
        });
    }

    @Override
    public void onReceive(Object message) throws Exception {
    }


}

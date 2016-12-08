package handlers;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import protocol.*;
import status.PacoteTransacao;
import status.Transacao;
import utils.AtoresCentral;

import java.util.HashMap;
import java.util.Map;

import static sun.jvm.hotspot.oops.CellTypeState.ref;

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

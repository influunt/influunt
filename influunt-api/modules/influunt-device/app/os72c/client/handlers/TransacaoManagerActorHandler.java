package os72c.client.handlers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import models.StatusDevice;
import os72c.client.handlers.transacoes.TransacaoBinder;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.DestinoCentral;
import protocol.Envelope;
import protocol.EtapaTransacao;
import protocol.TipoMensagem;
import status.Transacao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class TransacaoManagerActorHandler extends UntypedActor {
    private final Map<String, ActorRef> transacoes = new HashMap<>();

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private String idControlador;

    private Storage storage;

    public TransacaoManagerActorHandler(String idControlador, Storage storage) {
        this.idControlador = idControlador;
        this.storage = storage;
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("START TransacaoManagerActorHandler");
        super.preStart();
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO) && !storage.getStatus().equals(StatusDevice.NOVO)) {
                JsonNode transacaoJson = Json.parse(envelope.getConteudo().toString());
                Transacao transacao = Transacao.fromJson(transacaoJson);
                ActorRef ref = null;
                if (transacao.etapaTransacao.equals(EtapaTransacao.PREPARE_TO_COMMIT)) {
                    Class klass = TransacaoBinder.getClass(transacao.tipoTransacao);
                    ref = getContext().actorOf(Props.create(klass, idControlador, storage), "transacao-" + transacao.transacaoId);
                    transacoes.put(transacao.transacaoId, ref);
                } else {
                    ref = transacoes.get(transacao.transacaoId);
                }
                if (ref != null) {
                    ref.tell(envelope, getSelf());
                } else {
                    transacao.etapaTransacao = EtapaTransacao.ABORT;
                    envelope.setDestino(DestinoCentral.transacao(transacao.transacaoId));
                    envelope.setConteudo(transacao.toJson().toString());
                    getContext().actorSelection(AtoresDevice.mqttActorPath(idControlador)).tell(envelope, getSelf());
                }
            }
        }
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("STOP");
        super.postStop();
    }
}

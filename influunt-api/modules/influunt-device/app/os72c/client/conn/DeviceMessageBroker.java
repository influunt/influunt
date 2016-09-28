package os72c.client.conn;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.Router;
import os72c.client.handlers.ConfiguracaoActorHandler;
import os72c.client.handlers.EchoActorHandler;
import os72c.client.handlers.TransacaoActorHandler;
import os72c.client.protocols.Mensagem;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import os72c.client.storage.Storage;
import protocol.Envelope;
import protocol.TipoMensagem;

import java.util.HashMap;
import java.util.Map;

import static utils.MessageBrokerUtils.createRoutees;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class DeviceMessageBroker extends UntypedActor {

    Router routerEcho;

    ActorRef actorConfiguracao;

    Map<TipoMensagem, Router> routers = new HashMap<>();

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public DeviceMessageBroker(String idControlador, Storage storage) {

        routers.put(TipoMensagem.ECHO, createRoutees(getContext(), 5, EchoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO, createRoutees(getContext(), 1, ConfiguracaoActorHandler.class, idControlador, storage));
        routers.put(TipoMensagem.TRANSACAO, createRoutees(getContext(), 1, TransacaoActorHandler.class, idControlador, storage));
        actorConfiguracao = getContext().actorOf(Props.create(ConfiguracaoActorHandler.class, idControlador, storage), "actorConfig");

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (routers.containsKey(envelope.getTipoMensagem())) {
                routers.get(envelope.getTipoMensagem()).route(envelope, getSender());
            } else {
                log.error("MESSAGE BROKER NÃO SABER TRATAR O TIPO: {}", envelope.getTipoMensagem());
                throw new RuntimeException("MESSAGE BROKER NÃO SABER TRATAR O TIPO " + envelope.getTipoMensagem());
            }
        } else if (message instanceof Mensagem) {
            if (message instanceof MensagemVerificaConfiguracao) {
                actorConfiguracao.tell("VERIFICA", getSender());
            }
        }
    }
}

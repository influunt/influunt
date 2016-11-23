package server.conn;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.Router;
import handlers.*;
import protocol.Envelope;
import protocol.TipoMensagem;

import java.util.HashMap;
import java.util.Map;

import static utils.MessageBrokerUtils.createRoutees;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class CentralMessageBroker extends UntypedActor {

    Map<TipoMensagem, Router> routers = new HashMap<>();

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    {
        routers.put(TipoMensagem.CONTROLADOR_ONLINE, createRoutees(getContext(), 5, ConexaoOnlineActorHandler.class));
        routers.put(TipoMensagem.CONTROLADOR_OFFLINE, createRoutees(getContext(), 5, ConexaoOfflineActorHandler.class));
        routers.put(TipoMensagem.ECHO, createRoutees(getContext(), 5, EchoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO_INICIAL, createRoutees(getContext(), 5, ConfiguracaoActorHandler.class));
        routers.put(TipoMensagem.MUDANCA_STATUS_CONTROLADOR, createRoutees(getContext(), 5, MudancaStatusControladorActorHandler.class));
        routers.put(TipoMensagem.TRANSACAO, createRoutees(getContext(), 5, TransacaoActorHandler.class));
        routers.put(TipoMensagem.OK, createRoutees(getContext(), 5, OKActorHandler.class));
        routers.put(TipoMensagem.ERRO, createRoutees(getContext(), 5, ErroActorHandler.class));
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (routers.containsKey(envelope.getTipoMensagem())) {
                routers.get(envelope.getTipoMensagem()).route(envelope, getSender());
                log.error("CENTRAL RECEBEU: " + envelope.getTipoMensagem());
            } else {
                log.error("MESSAGE BROKER NÃO SABER TRATAR O TIPO: {}", envelope.getTipoMensagem());
                throw new RuntimeException("MESSAGE BROKER NÃO SABER TRATAR O TIPO " + envelope.getTipoMensagem());
            }
        }
    }
}
